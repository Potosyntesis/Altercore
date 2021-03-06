package com.example.altercore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import static com.example.altercore.MainCharacter.screenMove;

public class GameSurfaceView extends SurfaceView implements GameInterface,SurfaceHolder.Callback,Runnable, View.OnTouchListener , GestureDetector.OnGestureListener {

    private Canvas screenCanvas;
    private SurfaceHolder holder;
    private Thread thread;

    GestureDetector gestureDetector;

    Background background;
    BottomFloor floor;
    MainCharacter mainCharacter;
    PlatformSet1 platformSet1;
    PlatformSet2 platformSet2;
    ProgressBar progressBar;
    Buttons buttons;
    ExplosionSound explosionSound;
    HitSound hitSound;
    LaserSound laserSound;


    int playerScore = 0;
    boolean gameOver = false;

    public static boolean onPlatform = false;
    public static boolean isMoving = false;
    public static boolean isJump =  false;
    public static boolean moveLeft = false, moveRight = false, aPress = false, bPress = false;
    public static boolean rangedAttack = false;
    public static int playerHealth = 10;
    public static boolean playerHit = false;
    public static boolean enemy1Hit = false;
    public static boolean enemy2Hit = false;

    public GameSurfaceView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        this.setOnTouchListener(this);
        thread = new Thread(this);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        //initialise objects
        gestureDetector = new GestureDetector(this);
        background = new Background(getContext(),width,height);
        floor = new BottomFloor(getContext(),width,height);
        mainCharacter = new MainCharacter(getContext(),R.drawable.playerani,width);
        platformSet1 = new PlatformSet1(getContext(),width,height);
        platformSet2 = new PlatformSet2(getContext(),width,height);
        progressBar = new ProgressBar(getContext(),width,height);
        buttons = new Buttons(getContext());
        explosionSound = new ExplosionSound(getContext());
        laserSound = new LaserSound(getContext());
        hitSound = new HitSound(getContext());
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        //checks for button press on screen
        if (buttons.buttonLeft_r.contains((int)event.getX(),(int)event.getY())){
            isMoving = true;
            moveLeft = true;
        }

        if (buttons.buttonRight_r.contains((int)event.getX(),(int)event.getY())){
            isMoving = true;
            moveRight = true;
        }

        if (buttons.buttonA_r.contains((int)event.getX(),(int)event.getY())){

        }

        if (buttons.buttonB_r.contains((int)event.getX(),(int)event.getY())){
            rangedAttack = true;
            laserSound.play();
        }

        switch (event.getAction())
        {
            //sets bool to false if button not pressed
            case MotionEvent.ACTION_UP:
            {
                isMoving =false;
                rangedAttack = false;
                moveLeft = false;
                moveRight = false;
                aPress = false;
                bPress = false;
            }
        }
        return true;
    }


    @Override
    public void run() {
            while(true)
            {
                render(screenCanvas);
                try{
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                update();

                //check if player on platform
                if (platformCollision1() || platformCollision2()){
                    onPlatform = true;
                }else{
                    onPlatform = false;
                }
//                Log.i("test", "Player health = "+playerHealth);

                //check enemy hit by projectile
                if (enemyHit1()){
                    playerScore += 10;
                    platformSet1.enemySpawn.paint.setAlpha(0);
                    enemy1Hit = true;
                    mainCharacter.fire_Proj = false;
                    explosionSound.play();
                }else{
                    enemy1Hit = false;
                    explosionSound.stop();
                }

                //check enemy hit by projectile
                if (enemyHit2()){
                    playerScore += 10;
                    platformSet2.enemySpawn.paint.setAlpha(0);
                    enemy2Hit = true;
                    mainCharacter.fire_Proj = false;
                    explosionSound.play();
                }else{
                    enemy2Hit = false;
                    explosionSound.stop();
                }

                //check player hit by projectile
                if (playerHit()){
                    playerHealth --;
                    playerHit = true;
                    mainCharacter.paint.setAlpha(0);
                    hitSound.play();
                }else{
                    playerHit = false;
                    mainCharacter.paint.setAlpha(255);
                    hitSound.stop();
                }

                //ends the game when player health is 0
                if(playerHealth <= 0){
                    gameOver = true;
                    break;
                }

            }
    }

    @Override
    public void update() {
        progressBar.update();
        //moves the screen to make the player move
        if(isMoving && screenMove) {
            background.update();
            floor.update();
            platformSet1.update();
            platformSet2.update();
        }
//        Log.i("test","is Moving = "+isMoving+ ", moveLeft = "+moveLeft+", moveRight = "+moveRight);


    }

    @Override
    public void render(Canvas canvas) {
        if(holder.getSurface().isValid()){
            screenCanvas = holder.lockCanvas();
            background.render(screenCanvas);
            floor.render(screenCanvas);
            mainCharacter.render(screenCanvas);
            platformSet1.render(screenCanvas);
            platformSet2.render(screenCanvas);
            progressBar.render(screenCanvas);
            buttons.render(screenCanvas);
            holder.unlockCanvasAndPost(screenCanvas);
            //draws all the objects
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //player jump is done by fling
        float deltaX, deltaY;
        deltaX = e1.getX()+velocityX - e2.getX()+velocityY;
        deltaY = e1.getY()+velocityX- e2.getY()+velocityY;
        double degree = Math.atan2(deltaY,deltaX);
        int deg = (int) Math.toDegrees(degree);
//        Log.i("Testing"," Fling "+ deltaX +"    "+deltaY+ "tan   "+deg + " deg" );

        if(deg>50 && deg<130) {
            if (isJump == false) {
                isJump = true;
            }
        }
        return false;
    }


    public boolean platformCollision1(){
        //platform collision for the player to stand on the platform
        Rect player_copy = new Rect(mainCharacter.frameDest);

        Rect plat_copy1 = new Rect(platformSet1.plat_Rect1);
        Rect plat_copy2 = new Rect(platformSet1.plat_Rect2);
        Rect plat_copy3 = new Rect(platformSet1.plat_Rect3);

        plat_copy1.set(plat_copy1.left,plat_copy1.top + (plat_copy1.height()/2),plat_copy1.right,plat_copy1.bottom);
        plat_copy2.set(plat_copy2.left,plat_copy2.top + (plat_copy2.height()/2),plat_copy2.right,plat_copy2.bottom);
        plat_copy3.set(plat_copy3.left,plat_copy3.top + (plat_copy3.height()/2),plat_copy3.right,plat_copy3.bottom);

        if (plat_copy1.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy1.height(),player_copy.right,player_copy.bottom +plat_copy1.height())) {
            return true;
        }else if (plat_copy2.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy2.height(),player_copy.right,player_copy.bottom +plat_copy2.height())){
            return true;
        }else if (plat_copy3.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy3.height(),player_copy.right,player_copy.bottom +plat_copy3.height())){
            return true;
        }else{
            return false;
        }
    }

    public boolean platformCollision2(){
        //platform collision for the player to stand on the platform
        Rect player_copy = new Rect(mainCharacter.frameDest);

        Rect plat_copy1 = new Rect(platformSet2.plat_Rect1);
        Rect plat_copy2 = new Rect(platformSet2.plat_Rect2);
        Rect plat_copy3 = new Rect(platformSet2.plat_Rect3);

        plat_copy1.set(plat_copy1.left,plat_copy1.top + (plat_copy1.height()/2),plat_copy1.right,plat_copy1.bottom);
        plat_copy2.set(plat_copy2.left,plat_copy2.top + (plat_copy2.height()/2),plat_copy2.right,plat_copy2.bottom);
        plat_copy3.set(plat_copy3.left,plat_copy3.top + (plat_copy3.height()/2),plat_copy3.right,plat_copy3.bottom);

        if (plat_copy1.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy1.height(),player_copy.right,player_copy.bottom +plat_copy1.height())) {
            return true;
        }else if (plat_copy2.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy2.height(),player_copy.right,player_copy.bottom +plat_copy2.height())){
            return true;
        }else if (plat_copy3.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy3.height(),player_copy.right,player_copy.bottom +plat_copy3.height())){
            return true;
        }else{
            return false;
        }
    }

    public boolean enemyHit1(){
        //enemy collision
        Rect enemy1_copy = new Rect(platformSet1.enemySpawn.enemy_rect);

        if (enemy1_copy.intersect(mainCharacter.projectile.playerProj_rect)){
            return true;
        }else{
            return false;
        }
    }

    public boolean enemyHit2(){
        //enemy collision

        Rect enemy2_copy = new Rect(platformSet2.enemySpawn.enemy_rect);

        if (enemy2_copy.intersect(mainCharacter.projectile.playerProj_rect)){
            return true;
        }else{
            return false;
        }
    }

    public boolean playerHit(){
        //player collision

        Rect main_copy = new Rect(mainCharacter.frameDest);

        if (main_copy.intersect(platformSet1.enemySpawn.projectile.enemyProj_rect)){
            return true;
        }else if (main_copy.intersect(platformSet2.enemySpawn.projectile.enemyProj_rect)){
            return true;
        }else{
            return false;
        }
    }
}
