package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.altercore.BottomFloor.floorLine;
import static com.example.altercore.GameSurfaceView.moveRight;
import static com.example.altercore.MainCharacter.GroundPoint;

public class RangedEnemy implements GameInterface{

    EnemyProjectile projectile;

    Bitmap rangedEnemy;
    Rect enemy_rect;
    Paint paint;
    boolean fire = false;


    public RangedEnemy(Context context){

        projectile = new EnemyProjectile(context);

        rangedEnemy = BitmapFactory.decodeResource(context.getResources(),R.drawable.rangedenemy);
        rangedEnemy = Bitmap.createScaledBitmap(rangedEnemy,rangedEnemy.getWidth(),rangedEnemy.getHeight(),true);

        enemy_rect = new Rect(0,0,rangedEnemy.getWidth(),rangedEnemy.getHeight());

        paint = new Paint();
    }

    @Override
    public void update() {
        if(moveRight){
            enemy_rect.offset(-15,0);
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(rangedEnemy,null,enemy_rect,paint);
        projectile.render(canvas);
        if(!fire) {
            projectile.enemyProj_rect.offsetTo(enemy_rect.left, enemy_rect.top + 100);
            fire = true;
        }


//        projectile.update();

        if(projectile.enemyProj_rect.left<canvas.getWidth()){
            projectile.update();
        }else{
            fire = false;
        }

        if(projectile.enemyProj_rect.right<0){
            projectile.enemyProj_rect.offsetTo(enemy_rect.left+enemy_rect.width()/2,enemy_rect.top+100);
        }

    }
}
