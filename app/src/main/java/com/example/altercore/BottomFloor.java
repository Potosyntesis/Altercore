package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class BottomFloor implements GameInterface{

    Bitmap bg,floor;
    Rect back_Rect;
    Canvas thisCanvas;
    static int floorHeight = 0;
    Thread thread;

    ArrayList<Rect> floor_Arr = new ArrayList<>();

    Paint paint;


    public BottomFloor(Context context, int width, int height){
        bg = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        bg = Bitmap.createScaledBitmap(bg,width,height,true);
        floor =BitmapFactory.decodeResource(context.getResources(),R.drawable.ground);
        floor = Bitmap.createScaledBitmap(floor,100,100,true);

        back_Rect = new Rect(0,0,bg.getWidth(),bg.getHeight());

        for (int i = 0; i<Math.ceil(bg.getWidth()/floor.getWidth())+2;i++){
            floor_Arr.add(new Rect(i*floor.getWidth(),height-floor.getHeight(),(i+1)*floor.getWidth(),back_Rect.height()));
        }
    }



    @Override
    public void update() {
        for(Rect r:floor_Arr){
            r.offset(-15,0);
            if (r.right<0){
                r.offset(back_Rect.width()+floor.getWidth(),0);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        thisCanvas = canvas;

        for(Rect r:floor_Arr){
            canvas.drawBitmap(floor,null,r,null);
            floorHeight = floor.getHeight();
        }
    }
}
