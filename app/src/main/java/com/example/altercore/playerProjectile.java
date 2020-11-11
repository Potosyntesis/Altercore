package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.altercore.GameSurfaceView.rangedAttack;

public class playerProjectile implements GameInterface {


    Bitmap playerProjectile;
    Rect playerProj_rect;
    boolean fire = false;
    Paint paint;


    public playerProjectile(Context context){
        playerProjectile = BitmapFactory.decodeResource(context.getResources(),R.drawable.projectile1);
        playerProjectile = Bitmap.createScaledBitmap(playerProjectile,playerProjectile.getWidth()/2,playerProjectile.getHeight()/2,true);
        playerProj_rect = new Rect(0,0,playerProjectile.getWidth(),playerProjectile.getHeight());

        paint  = new Paint();

        paint.setAlpha(0);
        paint.setAntiAlias(false);
    }

    @Override
    public void update() {
        if(rangedAttack){
            paint.setAlpha(255);
            fire = true;
        }

        if(fire) {
            playerProj_rect.offset(50, 0);
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(playerProjectile,null,playerProj_rect,paint);

        if (playerProj_rect.left>canvas.getWidth()){
            paint.setAlpha(0);
            fire = false;

        }
    }
}
