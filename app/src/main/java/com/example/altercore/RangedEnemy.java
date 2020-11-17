package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static com.example.altercore.BottomFloor.floorLine;
import static com.example.altercore.GameSurfaceView.moveRight;
import static com.example.altercore.GameSurfaceView.playerHit;
import static com.example.altercore.MainCharacter.GroundPoint;

public class RangedEnemy implements GameInterface{

    EnemyProjectile projectile;

    Bitmap rangedEnemy;
    Rect enemy_rect;
    Paint paint;
    boolean fire = true;
    boolean proj_set = false;
    int proj_delay = 0;


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
        if(!proj_set) {
            projectile.enemyProj_rect.offsetTo(enemy_rect.left+enemy_rect.width()/2,enemy_rect.top+100);
            proj_set = true;
        }


//        projectile.update();

        if(projectile.enemyProj_rect.left<canvas.getWidth() && fire){
            projectile.update();
        }else{
            proj_set = false;
        }

        if(projectile.enemyProj_rect.right<=0 || playerHit){
            projectile.enemyProj_rect.offsetTo(0-projectile.enemyProj_rect.width(),projectile.enemyProj_rect.top);
            projectile.enemyProj_rect.offset(0,0);
            fire = false;
            proj_set = true;
            proj_delay++;

            if (proj_delay>=80){
                proj_set = false;
                fire = true;
                proj_delay = 0;
            }
        }

        Log.i("test","Proj set = "+proj_set+", fire = "+fire+", proj delay = "+proj_delay);

    }
}
