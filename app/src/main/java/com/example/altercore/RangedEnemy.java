package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class RangedEnemy implements GameInterface{

    Bitmap rangedEnemy;
    Rect enemy_rect;
    Paint paint;


    public RangedEnemy(Context context){
        rangedEnemy = BitmapFactory.decodeResource(context.getResources(),R.drawable.rangedenemy);
        rangedEnemy = Bitmap.createScaledBitmap(rangedEnemy,rangedEnemy.getWidth(),rangedEnemy.getHeight(),true);

        enemy_rect = new Rect(0,0,rangedEnemy.getWidth(),rangedEnemy.getHeight());

        paint = new Paint();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(rangedEnemy,null,enemy_rect,paint);
    }
}
