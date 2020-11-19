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
    Paint paint;

    public EnemyProjectile(Context context){
        //initialise the image

        enemyProjectile = BitmapFactory.decodeResource(context.getResources(),R.drawable.projectile2);
        enemyProjectile = Bitmap.createScaledBitmap(enemyProjectile,enemyProjectile.getWidth()/2,enemyProjectile.getHeight()/2,true);

        enemyProj_rect= new Rect(0,0,enemyProjectile.getWidth(),enemyProjectile.getHeight());

        paint = new Paint();

        paint.setAntiAlias(false);
        paint.setAlpha(255);

    }

    @Override
    public void update() {
        //moves the porjectile
        paint.setAlpha(255);
        enemyProj_rect.offset(-35,0);
    }

    @Override
    public void render(Canvas canvas) {
        //draws the projectile
        canvas.drawBitmap(enemyProjectile,null,enemyProj_rect,paint);

    }
}
