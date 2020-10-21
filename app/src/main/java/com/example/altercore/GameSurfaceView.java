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

import androidx.annotation.NonNull;

public class GameSurfaceView extends SurfaceView implements GameInterface,SurfaceHolder.Callback,Runnable, View.OnTouchListener , GestureDetector.OnGestureListener {

    private Canvas screenCanvas;
    private SurfaceHolder holder;
    private Thread thread;

    GestureDetector gestureDetector;

    Background background;
    BottomFloor floor;
    MainCharacter mainCharacter;

    long time;
    public static boolean isMoving = false;
    public static boolean isJump =  false;

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
        mainCharacter = new MainCharacter(getContext(),R.drawable.player);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                isMoving =false;
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
            }
    }

    @Override
    public void update() {
        if(isMoving) {
            background.update();
            floor.update();

        }
        //Log.i("test",String.valueOf(isMoving));
    }

    @Override
    public void render(Canvas canvas) {
        if(holder.getSurface().isValid()){
            screenCanvas = holder.lockCanvas();
            background.render(screenCanvas);
            floor.render(screenCanvas);
            mainCharacter.render(screenCanvas);
            holder.unlockCanvasAndPost(screenCanvas);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        isMoving = true;
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
        isJump = true;
        Log.d("Testing"," Fling is true" );
        return false;
    }
}
