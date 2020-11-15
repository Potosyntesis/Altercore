package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class EnemyProjectile implements GameInterface{

    Bitmap enemyProjectile;
    Rect enemyProj_rect;
    boolean offScreen = false;
    Paint paint;

    public EnemyProjectile(Context context){
        enemyProjectile = BitmapFactory.decodeResource(context.getResources(),R.drawable.projectile2);
        enemyProjectile = Bitmap.createScaledBitmap(enemyProjectile,enemyProjectile.getWidth()/2,enemyProjectile.getHeight()/2,true);

        enemyProj_rect= new Rect(0,0,enemyProjectile.getWidth(),enemyProjectile.getHeight());

        paint = new Paint();

        paint.setAntiAlias(false);

    }

    @Override
    public void update() {
        paint.setAlpha(255);

        if (!offScreen) {
            enemyProj_rect.offset(-50, 0);
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(enemyProjectile,null,enemyProj_rect,paint);

        if(enemyProj_rect.right<0){
            paint.setAlpha(0);
            offScreen = true;
        }
    }
}
