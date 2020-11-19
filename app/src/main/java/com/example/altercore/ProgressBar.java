package com.example.altercore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.altercore.GameSurfaceView.playerHealth;

public class ProgressBar implements GameInterface{

    Rect back_rect,health_rect;
    Paint back_p,health_p;

    private int screenWidth;
    private int healthWidth;

    public ProgressBar(Context context, int width, int height){

        //make a custom progress bar with rects
        screenWidth = width;

        back_rect = new Rect(0,0,screenWidth/2,50);
        health_rect = new Rect(5,5,(screenWidth/2)-5,45);

        healthWidth = health_rect.width();

        back_p = new Paint();
        health_p = new Paint();

        back_p.setColor(Color.BLACK);
        health_p.setColor(Color.RED);
    }

    @Override
    public void update() {
        //redraw the progress par to got down for player health
        back_rect.set(back_rect.left,back_rect.top,healthWidth+5,back_rect.bottom);
        health_rect.set(health_rect.left,health_rect.top,(healthWidth/10)*playerHealth,health_rect.bottom);
    }

    @Override
    public void render(Canvas canvas) {
        //draw the progress bar
        back_rect.offsetTo(10,10);
        health_rect.offsetTo(15,15);

        canvas.drawRect(back_rect,back_p);
        canvas.drawRect(health_rect,health_p);
    }
}
