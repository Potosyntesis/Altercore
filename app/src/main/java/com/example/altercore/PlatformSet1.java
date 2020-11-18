package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static com.example.altercore.GameSurfaceView.enemy1Hit;
import static com.example.altercore.GameSurfaceView.moveLeft;
import static com.example.altercore.GameSurfaceView.moveRight;

public class PlatformSet1 implements GameInterface{

    RangedEnemy enemySpawn;

    private boolean first = true;
    private int screen_sect;
    Rect back_rect,plat_Rect1,plat_Rect2,plat_Rect3;
    Bitmap back,platform, platform2, platform3;
    Random rand;
    Paint paint;

    int array_counter = 0;
    int[] PlatformPlacment = {2,2,1,1,2,1,3,2,3,2,1,1};


    public PlatformSet1(Context context, int width, int height){
        enemySpawn = new RangedEnemy(context);

        back = BitmapFactory.decodeResource(context.getResources(),R.drawable.citybackground);
        back = Bitmap.createScaledBitmap(back,width,height,true);

        platform = BitmapFactory.decodeResource(context.getResources(),R.drawable.platforms);
        platform = Bitmap.createScaledBitmap(platform,back.getWidth()/3,(platform.getHeight()/4)*2,true);

        platform2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.platforms);
        platform2 = Bitmap.createScaledBitmap(platform2,back.getWidth()/3,(platform2.getHeight()/4)*2,true);

        platform3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.platforms);
        platform3 = Bitmap.createScaledBitmap(platform3,back.getWidth()/3,(platform3.getHeight()/4)*2,true);

        back_rect = new Rect(0,0,back.getWidth(),back.getHeight());
        plat_Rect1 = new Rect(0,0,platform.getWidth(),platform.getHeight());
        plat_Rect2 = new Rect(0,0,platform2.getWidth(),platform2.getHeight());
        plat_Rect3 = new Rect(0,0,platform3.getWidth(),platform3.getHeight());

        rand = new Random();
        paint = new Paint();
        paint.setAntiAlias(false);
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

            if(enemySpawn.enemy_rect.right < 0){
                enemySpawn.enemy_rect.offsetTo(plat_Rect2.left+(plat_Rect2.width()/2),plat_Rect2.top-enemySpawn.enemy_rect.height());
                enemySpawn.paint.setAlpha(255);
                enemy1Hit = false;
                enemySpawn.enemyHit = false;
            }


        }else if (moveLeft){
            plat_Rect1.offset(0,0);
            plat_Rect2.offset(0, 0);
            plat_Rect3.offset(0, 0);
        }
        enemySpawn.update();

    }

    @Override
    public void render(Canvas canvas) {
        if(first) {
            screen_sect = back_rect.height() / 4;
            plat_Rect1.offsetTo(back_rect.width(), (screen_sect * PlatformPlacment[array_counter])-platform.getHeight()-10);
            array_counter++;
            plat_Rect2.offsetTo(plat_Rect1.right+250, (screen_sect * PlatformPlacment[array_counter])-platform.getHeight()-10);
            array_counter++;
            plat_Rect3.offsetTo(plat_Rect2.right+250, (screen_sect * PlatformPlacment[array_counter])-platform.getHeight()-10);
            array_counter++;

            enemySpawn.enemy_rect.offsetTo(plat_Rect2.left+(plat_Rect2.width()/2),plat_Rect2.top-enemySpawn.enemy_rect.height());

            first = false;
        }
        canvas.drawBitmap(platform,null,plat_Rect1,paint);
        canvas.drawBitmap(platform2,null,plat_Rect2,paint);
        canvas.drawBitmap(platform3,null,plat_Rect3,paint);

        enemySpawn.render(canvas);
        if (enemy1Hit) {
            enemySpawn.enemyHit = true;
        }

    }
}
