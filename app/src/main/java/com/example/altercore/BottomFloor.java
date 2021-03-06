package com.example.altercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import static com.example.altercore.GameSurfaceView.moveLeft;
import static com.example.altercore.GameSurfaceView.moveRight;

public class BottomFloor implements GameInterface{

    Bitmap bg,floor;
    Rect back_Rect;
    Canvas thisCanvas;
    private static int floorHeight = 0;

    int canvas_x;

    public static int floorLine = 0;
    ArrayList<Rect> floor_Arr = new ArrayList<>();

    Paint paint;


    public BottomFloor(Context context, int width, int height){
        //initialise the floor and background for reference

        bg = BitmapFactory.decodeResource(context.getResources(),R.drawable.citybackground);
        bg = Bitmap.createScaledBitmap(bg,width,height,true);
        floor =BitmapFactory.decodeResource(context.getResources(),R.drawable.groundtile);
        floor = Bitmap.createScaledBitmap(floor,100,100,true);

        paint = new Paint();
        paint.setAntiAlias(false);

        canvas_x = width;

        floorLine = floor.getHeight();
        back_Rect = new Rect(0,0,bg.getWidth(),bg.getHeight());

        for (int i = 0; i<Math.ceil(bg.getWidth()/floor.getWidth())+2;i++){
            floor_Arr.add(new Rect(i*floor.getWidth(),height-floor.getHeight(),(i+1)*floor.getWidth(),back_Rect.height()));
        }
    }



    @Override
    public void update() {
        //moves the floor when the player moves
        for(Rect r:floor_Arr){
            if(moveRight) {
                r.offset(-15, 0);
                if (r.right < 0) {
                    r.offset(back_Rect.width() + floor.getWidth(), 0);
                }
            }else if (moveLeft){
                r.offset(0,0);
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        //draws the floor
        thisCanvas = canvas;

        for(Rect r:floor_Arr){
            canvas.drawBitmap(floor,null,r,paint);
            floorHeight = floor.getHeight();
        }
    }
}
