package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import static com.example.altercore.GameSurfaceView.rangedAttack;

public class Projectiles implements GameInterface{

    Bitmap playerProjectile, enemyProjectile;
    Rect playerProj_rect,enemyProj_rect;

    public Projectiles(Context context){

        playerProjectile = BitmapFactory.decodeResource(context.getResources(),R.drawable.projectile1);
        enemyProjectile = BitmapFactory.decodeResource(context.getResources(),R.drawable.projectile2);

        playerProjectile = Bitmap.createScaledBitmap(playerProjectile,playerProjectile.getWidth()*2,playerProjectile.getHeight()*2,true);
        enemyProjectile = Bitmap.createScaledBitmap(enemyProjectile,enemyProjectile.getWidth()*2,enemyProjectile.getHeight()*2,true);

        playerProj_rect = new Rect(0,0,playerProjectile.getWidth()*2,playerProjectile.getHeight()*2);
        enemyProj_rect = new Rect(0,0,enemyProjectile.getWidth()*2,enemyProjectile.getHeight()*2);
    }

    @Override
    public void update() {
        if(rangedAttack){
            playerProj_rect.offsetTo(15,0);
        }
    }

    @Override
    public void render(Canvas canvas) {
//        playerProj_rect.offsetTo();
        canvas.drawBitmap(playerProjectile,null,playerProj_rect,null);
    }
}
