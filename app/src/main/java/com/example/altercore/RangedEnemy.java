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
    private boolean fire = true;
    private boolean proj_setup = false;
    private int proj_delay = 0;
    private boolean reset = false;
    public boolean enemyHit = false;


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
        projectile.render(canvas);
        canvas.drawBitmap(rangedEnemy,null,enemy_rect,paint);
        if (!enemyHit) {
            if (!proj_setup) {
                projectile.enemyProj_rect.offsetTo(enemy_rect.left + enemy_rect.width() / 2, enemy_rect.top + 100);
                proj_setup = true;
            }

            if (projectile.enemyProj_rect.left < canvas.getWidth() && fire) {
                projectile.update();
            } else {
                proj_setup = false;
            }

            enemyAttackReset();
        }
//        Log.i("test","Proj set = "+proj_setup+", fire = "+fire+", proj delay = "+proj_delay+", player hit = "+playerHit);
    }

    private void enemyAttackReset(){

        if(projectile.enemyProj_rect.right<=0 || playerHit){
            projectile.enemyProj_rect.offsetTo(0,0);
            projectile.paint.setAlpha(0);
            reset = true;
        }

        if (reset){
            fire = false;
            proj_setup = true;
            proj_delay++;

            if (proj_delay>=80){
                proj_setup = false;
                fire = true;
                reset = false;
                proj_delay = 0;
            }
        }

    }
}
