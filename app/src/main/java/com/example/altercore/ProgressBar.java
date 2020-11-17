package com.example.altercore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class ProgressBar implements GameInterface{

    Rect back_rect,health_rect;
    Paint back_p,health_p;

    public ProgressBar(Context context, int width, int height){
        back_rect = new Rect(0,0,width/2,50);
        health_rect = new Rect(5,5,(width/2)-5,45);

        back_p = new Paint();
        health_p = new Paint();

        back_p.setColor(Color.BLACK);
        health_p.setColor(Color.RED);
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Canvas canvas) {
        back_rect.offsetTo(10,10);
        health_rect.offsetTo(15,15);

        canvas.drawRect(back_rect,back_p);
        canvas.drawRect(health_rect,health_p);
    }
}
