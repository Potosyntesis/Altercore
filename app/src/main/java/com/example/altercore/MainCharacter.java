package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import static com.example.altercore.BottomFloor.floorLine;
import static com.example.altercore.GameSurfaceView.isJump;
import static com.example.altercore.GameSurfaceView.isMoving;
import static com.example.altercore.GameSurfaceView.moveLeft;
import static com.example.altercore.GameSurfaceView.moveRight;
import static com.example.altercore.GameSurfaceView.onPlatform;

public class MainCharacter implements GameInterface, Runnable{

    int updateRate = 9;
    int jumpSpeed = -25;
    int canvas_x;
    public static int GroundPoint;
    public static boolean screenMove = false;
    boolean onGround = false;
    boolean justLanded = true;
    boolean lookRight = true;

    Matrix matrix;
    Rect frameSelect;
    Rect frameDest;
    Point frameID = new Point();
    Point frameSize = new Point(3,1);
    Bitmap main, main_ani;

    public MainCharacter(Context context, int shape, int width){
        main = BitmapFactory.decodeResource(context.getResources(),shape);
        main = Bitmap.createScaledBitmap(main,480,150,true);


        canvas_x = width;

        frameSelect = new Rect(0,0,(main.getWidth()/frameSize.x),main.getHeight());

        frameDest = new Rect(frameSelect);
        frameDest.offsetTo(canvas_x/3,0);

        new Thread(this).start();
        matrix = new Matrix();
    }

    @Override
    public void update() {
        if(isMoving){
            frameID.x++;
        }else{
            frameID.x = 2;
        }

        frameID.x %= frameSize.x;
        frameSelect = new Rect(frameID.x*main.getWidth()/frameSize.x,frameID.y*main.getHeight(),(frameID.x+1)*main.getWidth()/frameSize.x, main.getHeight());

    }

    @Override
    public void render(Canvas canvas) {
        GroundPoint = (canvas.getHeight() - main.getHeight()) - floorLine;

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
//        Log.i("Frame Dest: ",String.valueOf(frameDest));

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

        canvas.drawBitmap(main, frameSelect, frameDest, null);
    }

    @Override
    public void run() {
        while(true){
            update();
            try{
                Thread.sleep(1000/updateRate);
            }catch (Exception e){}
        }
    }

    private void rangedAttack(){

    }
}
