package com.example.altercore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

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
    Platforms platforms;
    Buttons buttons;

    public static boolean onPlatform = false;
    public static boolean isMoving = false;
    public static boolean isJump =  false;
    public static boolean moveLeft = false, moveRight = false, aPress = false, bPress = false;

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
        gestureDetector = new GestureDetector(this);
        background = new Background(getContext(),width,height);
        floor = new BottomFloor(getContext(),width,height);
        mainCharacter = new MainCharacter(getContext(),R.drawable.player,width);
        platforms = new Platforms(getContext(),width,height);
        buttons = new Buttons(getContext());
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        gestureDetector.onTouchEvent(event);

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

        }

        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                isMoving =false;
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
                if (platformCollision()){
                    onPlatform = true;
                }else{
                    onPlatform = false;
                }
                //Log.i("On Platform:",String.valueOf(onPlatform));
            }
    }

    @Override
    public void update() {
        if(isMoving && screenMove) {
            background.update();
            floor.update();
            platforms.update();
        }
        Log.i("test","is Moving = "+isMoving+ ", moveLeft = "+moveLeft+", moveRight = "+moveRight);
    }

    @Override
    public void render(Canvas canvas) {
        if(holder.getSurface().isValid()){
            screenCanvas = holder.lockCanvas();
            background.render(screenCanvas);
            floor.render(screenCanvas);
            mainCharacter.render(screenCanvas);
            platforms.render(screenCanvas);
            buttons.render(screenCanvas);
            holder.unlockCanvasAndPost(screenCanvas);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //isMoving = true;
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
        float deltaX, deltaY;
        deltaX = e1.getX()+velocityX - e2.getX()+velocityY;
        deltaY = e1.getY()+velocityX- e2.getY()+velocityY;
        double degree = Math.atan2(deltaY,deltaX);
        int deg = (int) Math.toDegrees(degree);
        Log.i("Testing"," Fling "+ deltaX +"    "+deltaY+ "tan   "+deg + " deg" );

        if(deg>50 && deg<130) {
            if (isJump == false) {
                isJump = true;
            }
        }
        return false;
    }


    public boolean platformCollision(){
        Rect player_copy = new Rect(mainCharacter.frameDest);
        Rect plat_copy = new Rect(platforms.plat_Rect);
        plat_copy.set(plat_copy.left,plat_copy.top + (plat_copy.height()/2),plat_copy.right,plat_copy.bottom);
        if (plat_copy.intersect(player_copy.left,player_copy.top+player_copy.height()+plat_copy.height(),player_copy.right,player_copy.bottom +plat_copy.height()))
        {
            return true;
        }else{
            return false;
        }
    }
}
