package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Background implements GameInterface{

    Bitmap back;
    Canvas thisCanvas;
    int canvas_x,canvas_y;
    Paint paint;

    ArrayList<Rect> back_arr = new ArrayList<>();

    public Background(Context context ,int width, int height){
        back = BitmapFactory.decodeResource(context.getResources(),R.drawable.citybackground);
        back = Bitmap.createScaledBitmap(back,width,height,true);

        canvas_x = width;
        canvas_y = height;

        for(int i = 0;i < Math.ceil(back.getWidth()/3);i++){
            back_arr.add(new Rect(i*back.getWidth(),height-back.getHeight(),(i+1)*back.getWidth(),height));
        }
    }


    @Override
    public void update() {
        for (Rect r:back_arr)
        {
            r.offset(-15,0);
            if(r.right<0){
                r.offset(back.getWidth(),0);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        thisCanvas = canvas;

        for (Rect r : back_arr) {
            canvas.drawBitmap(back, null,r, null);
        }
    }
}