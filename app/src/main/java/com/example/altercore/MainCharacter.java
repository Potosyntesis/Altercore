package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import static com.example.altercore.BottomFloor.floorLine;
import static com.example.altercore.GameSurfaceView.isJump;
import static com.example.altercore.GameSurfaceView.isMoving;

public class MainCharacter implements GameInterface, Runnable{

    int updateRate = 9;
    public static int GroundPoint;
    Rect frameSelect;
    Rect frameDest;
    Point frameID = new Point();
    Point frameSize = new Point(3,1);
    Bitmap main;

    public MainCharacter(Context context, int shape){
        main = BitmapFactory.decodeResource(context.getResources(),shape);
        main = Bitmap.createScaledBitmap(main,480,150,true);
        frameSelect = new Rect(0,0,(main.getWidth()/frameSize.x),main.getHeight());
        frameDest = new Rect(frameSelect);
        frameDest.offsetTo(300,0);
        new Thread(this).start();
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
        GroundPoint = (canvas.getHeight()-main.getHeight())-floorLine;
        //Log.i("test",String.valueOf(onGround));
        if(isJump == false) {
            if (frameDest.top < GroundPoint) {
                frameDest.offset(0, 20);
            } else {
                frameDest.offsetTo(300, GroundPoint);
            }
        }else{
            if(frameDest.top>GroundPoint-300){
                frameDest.offset(0, -20);
            }else{
                isJump = false;
            }
        }
        canvas.drawBitmap(main,frameSelect,frameDest,null);
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
}
