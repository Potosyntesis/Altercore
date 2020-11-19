package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Buttons implements GameInterface{

    Paint paint;
    Bitmap buttonLeft,buttonRight, buttonA, buttonB;
    Rect buttonLeft_r,buttonRight_r,buttonA_r,buttonB_r;

    public Buttons(Context context){
        paint = new Paint();
        paint.setAlpha(160);
        paint.setAntiAlias(false);

        //initialise the button images

        buttonLeft = BitmapFactory.decodeResource(context.getResources(),R.drawable.leftbutton);
        buttonRight = BitmapFactory.decodeResource(context.getResources(),R.drawable.rightbutton);
        buttonA = BitmapFactory.decodeResource(context.getResources(),R.drawable.buttona);
        buttonB = BitmapFactory.decodeResource(context.getResources(),R.drawable.buttonb);

        buttonLeft = Bitmap.createScaledBitmap(buttonLeft,180,180,true);
        buttonRight = Bitmap.createScaledBitmap(buttonRight,180,180,true);
        buttonA = Bitmap.createScaledBitmap(buttonA,180,180,true);
        buttonB = Bitmap.createScaledBitmap(buttonB,180,180,true);

        buttonLeft_r = new Rect(0,0,buttonLeft.getWidth(),buttonLeft.getHeight());
        buttonRight_r = new Rect(0,0,buttonRight.getWidth(),buttonRight.getHeight());
        buttonA_r = new Rect(0,0,buttonA.getWidth(),buttonA.getHeight());
        buttonB_r = new Rect(0,0,buttonB.getWidth(),buttonB.getHeight());

    }

    @Override
    public void update() {
    }

    @Override
    public void render(Canvas canvas) {
        //draws the buttons
        canvas.drawBitmap(buttonLeft,null,buttonLeft_r,paint);
        canvas.drawBitmap(buttonRight,null,buttonRight_r,paint);
        canvas.drawBitmap(buttonA,null,buttonA_r,paint);
        canvas.drawBitmap(buttonB,null,buttonB_r,paint);

        buttonLeft_r.offsetTo(100,canvas.getHeight()- buttonLeft.getHeight() -120);
        buttonRight_r.offsetTo(150+ buttonLeft.getWidth(),canvas.getHeight()- buttonRight.getHeight() -120);

        buttonA_r.offsetTo(canvas.getWidth()-(buttonB.getWidth()*2)-150,canvas.getHeight()- buttonA.getHeight() -90);
        buttonB_r.offsetTo(canvas.getWidth()-buttonB.getWidth() - 100,canvas.getHeight()- buttonB.getHeight() -190);

    }
}
