package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import static com.example.altercore.BottomFloor.floorLine;
import static com.example.altercore.GameSurfaceView.isJump;
import static com.example.altercore.GameSurfaceView.isMoving;
import static com.example.altercore.GameSurfaceView.moveLeft;
import static com.example.altercore.GameSurfaceView.moveRight;
import static com.example.altercore.GameSurfaceView.onPlatform;
import static com.example.altercore.GameSurfaceView.rangedAttack;


public class MainCharacter implements GameInterface, Runnable{

    playerProjectile projectile;

    private int updateRate = 9;
    private int jumpSpeed = -25;
    private int canvas_x;
    public static int GroundPoint;
    public static boolean screenMove = false;
    private boolean onGround = false;
    private boolean justLanded = true;
    private boolean lookRight = true;
    public boolean fire_Proj = false;

    Rect frameSelect;
    Rect frameDest;
    Point frameID = new Point();
    Point frameSize = new Point(4,3);
    Bitmap main;
    Paint paint;

    public MainCharacter(Context context, int shape, int width){
        //initialise
        projectile = new playerProjectile(context);

        main = BitmapFactory.decodeResource(context.getResources(),shape);
        main = Bitmap.createScaledBitmap(main,main.getWidth(),main.getHeight(),true);


        canvas_x = width;

        frameSelect = new Rect(0,0,(main.getWidth()/frameSize.x),(main.getHeight()/frameSize.y));

        frameDest = new Rect(frameSelect);
        frameDest.offsetTo(canvas_x/3,0);

        paint = new Paint();

        new Thread(this).start();
    }

    @Override
    public void update() {
        //Player animation done by sprite sheet
        if(moveRight){
            frameID.x ++;
            frameID.y = 0;
            frameID.x %= 3;
        }else if(moveLeft){
            frameID.y = 1;
            frameID.x++;
            frameID.x %= 3;
        }else{
            frameID.x = 2;
            frameID.y = 0;
        }

        if (rangedAttack){
            frameID.y = 2;
            frameID.x = 3;
        }
        frameSelect = new Rect(frameID.x*main.getWidth()/frameSize.x,frameID.y*main.getHeight()/frameSize.y,(frameID.x+1)*main.getWidth()/frameSize.x, (frameID.y+1)*main.getHeight()/frameSize.y);

    }

    @Override
    public void render(Canvas canvas) {
        GroundPoint = (canvas.getHeight() - main.getHeight()/3) - floorLine;

        //Character Movement left and right
        if (moveRight){
            lookRight = true;
            if(frameDest.left<canvas_x/3){
                frameDest.offset(15,0);
                screenMove = false;
            }else{
                frameDest.offsetTo((canvas_x/3),frameDest.top);
                screenMove = true;
            }
        }else if (moveLeft){
            lookRight = false;
            if(frameDest.left>0){
                frameDest.offset(-15,0);
                screenMove = false;
            }else{
                frameDest.offsetTo(0,frameDest.top);
                screenMove = false;
            }
        }
        jump();
        playerAttack();
        projectile.render(canvas);
        canvas.drawBitmap(main, frameSelect, frameDest, paint);
    }

    //to play animations
    @Override
    public void run() {
        while(true){
            update();
            try{
                Thread.sleep(1000/updateRate);
            }catch (Exception e){}
        }
    }


    private void jump(){
        //jumping from the ground
        if(!onPlatform) {
            justLanded = true;
            if (isJump) {
                onGround = false;
                if (jumpSpeed <= 20) {
                    if (frameDest.top<0){
                        jumpSpeed = 20;
                    }
                    frameDest.offset(0, jumpSpeed);
                    jumpSpeed++;
                } else {
                    jumpSpeed = -25;
                    isJump = false;
                }
            } else if (frameDest.top < GroundPoint) {
                frameDest.offset(0, 20);
            } else if (frameDest.top >= GroundPoint) {
                onGround = true;
                frameDest.offsetTo(frameDest.left, GroundPoint);
            }
        }else{
            //jumping from the platform
            if(justLanded) {
                jumpSpeed = -25;
                isJump = false;
                onGround = true;
                justLanded = false;
            }
            if (isJump) {
                onGround = false;
                if (jumpSpeed <= 20) {
                    if (frameDest.top<0){
                        jumpSpeed = 20;
                    }
                    frameDest.offset(0, jumpSpeed);
                    jumpSpeed++;
                } else {
                    jumpSpeed = -25;
                    isJump = false;
                }
            } else{
                frameDest.offset(0, 0);
            }
        }
    }

    private void playerAttack(){

        //fire projectile if button pressed and reset
        if (rangedAttack){
            projectile.playerProj_rect.offsetTo(frameDest.left, frameDest.top+40);
            fire_Proj = true;
        }

        if(projectile.playerProj_rect.left>canvas_x){
            fire_Proj = false;
        }

        if (fire_Proj){
            projectile.update();
        }else if(!fire_Proj) {
            projectile.playerProj_rect.offsetTo(0,0);
            projectile.paint.setAlpha(0);
        }

    }
}
