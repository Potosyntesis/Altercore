package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import static com.example.altercore.GameSurfaceView.moveLeft;
import static com.example.altercore.GameSurfaceView.moveRight;

public class Platforms implements GameInterface{

    boolean first = true;
    int screen_sect;
    Rect back_rect,plat_Rect;
    Bitmap back,platform;

    public Platforms(Context context, int width, int height){
        back = BitmapFactory.decodeResource(context.getResources(),R.drawable.citybackground);
        back = Bitmap.createScaledBitmap(back,width,height,true);

        platform = BitmapFactory.decodeResource(context.getResources(),R.drawable.platform);
        platform = Bitmap.createScaledBitmap(platform,platform.getWidth(),(platform.getHeight()/3)*2,true);

        back_rect = new Rect(0,0,back.getWidth(),back.getHeight());
        plat_Rect = new Rect(0,0,platform.getWidth(),platform.getHeight());
    }

    @Override
    public void update() {
        if (moveRight) {
            plat_Rect.offset(-15, 0);
        }else if (moveLeft){
            plat_Rect.offset(0,0);
        }

    }

    @Override
    public void render(Canvas canvas) {
        if(first) {
            screen_sect = back_rect.height() / 4;
            plat_Rect.offsetTo(back_rect.width() / 2, (screen_sect * 3)-platform.getHeight());

            first = false;
        }
        canvas.drawBitmap(platform,null,plat_Rect,null);
    }
}
