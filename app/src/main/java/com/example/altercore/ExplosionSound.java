package com.example.altercore;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;

public class ExplosionSound implements GameInterface {
    SoundPool soundPool;
    int explosion;

    public ExplosionSound(Context context){
        //initialise the sound
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        explosion = soundPool.load(context,R.raw.explosion,1);
    }

    //play sound
    public void play(){
        soundPool.play(explosion,1.0f,1.0f,1,0,1.0f);
    }

    public void stop(){
        soundPool.autoPause();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Canvas canvas) {

    }
}
