package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import static com.example.altercore.GameSurfaceView.moveLeft;
import static com.example.altercore.GameSurfaceView.moveRight;

public class PlatformSet2 implements GameInterface{

    boolean first = true;
    int screen_sect;
    Rect back_rect,plat_Rect1,plat_Rect2,plat_Rect3;
    Bitmap back,platform, platform2, platform3;
    Random rand;

    int array_counter = 0;
    int[] PlatformPlacment = {2,1,3,3,1,2,1,3,1,3,2,3};

    public PlatformSet2(Context context, int width, int height){
        back = BitmapFactory.decodeResource(context.getResources(),R.drawable.citybackground);
        back = Bitmap.createScaledBitmap(back,width,height,true);

        platform = BitmapFactory.decodeResource(context.getResources(),R.drawable.platform);
        platform = Bitmap.createScaledBitmap(platform,back.getWidth()/3,(platform.getHeight()/3)*2,true);

        platform2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.platform);
        platform2 = Bitmap.createScaledBitmap(platform2,back.getWidth()/3,(platform2.getHeight()/3)*2,true);

        platform3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.platform);
        platform3 = Bitmap.createScaledBitmap(platform3,back.getWidth()/3,(platform3.getHeight()/3)*2,true);

        back_rect = new Rect(0,0,back.getWidth(),back.getHeight());
        plat_Rect1 = new Rect(0,0,platform.getWidth(),platform.getHeight());
        plat_Rect2 = new Rect(0,0,platform2.getWidth(),platform2.getHeight());
        plat_Rect3 = new Rect(0,0,platform3.getWidth(),platform3.getHeight());

        rand = new Random();
    }

    @Override
    public void update() {
        if (moveRight) {
            plat_Rect1.offset(-15, 0);
            plat_Rect2.offset(-15, 0);
            plat_Rect3.offset(-15, 0);

            if(array_counter > 11){
                array_counter = 0;
            }else {
                if (plat_Rect1.right < 0) {
                    plat_Rect1.offsetTo(back_rect.width(), (screen_sect * PlatformPlacment[array_counter]) - platform.getHeight());
                    array_counter++;
                } else if (plat_Rect2.right < 0) {
                    plat_Rect2.offsetTo(back_rect.width(), (screen_sect * PlatformPlacment[array_counter]) - platform.getHeight());
                    array_counter++;
                } else if (plat_Rect3.right < 0) {
                    plat_Rect3.offsetTo(back_rect.width(), (screen_sect * PlatformPlacment[array_counter]) - platform.getHeight());
                    array_counter++;
                }
            }


        }else if (moveLeft){
            plat_Rect1.offset(0,0);
            plat_Rect2.offset(0, 0);
            plat_Rect3.offset(0, 0);
        }

    }

    @Override
    public void render(Canvas canvas) {
        if(first) {
            screen_sect = back_rect.height() / 4;
            plat_Rect1.offsetTo(back_rect.width(), (screen_sect * PlatformPlacment[array_counter])-platform.getHeight());
            array_counter++;
            plat_Rect2.offsetTo(plat_Rect1.right+250, (screen_sect * PlatformPlacment[array_counter])-platform.getHeight());
            array_counter++;
            plat_Rect3.offsetTo(plat_Rect2.right+250, (screen_sect * PlatformPlacment[array_counter])-platform.getHeight());
            array_counter++;

            first = false;
        }
        canvas.drawBitmap(platform,null,plat_Rect1,null);
        canvas.drawBitmap(platform2,null,plat_Rect2,null);
        canvas.drawBitmap(platform3,null,plat_Rect3,null);


    }
}
