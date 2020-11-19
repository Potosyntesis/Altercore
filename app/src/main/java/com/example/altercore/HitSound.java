package com.example.altercore;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;

public class HitSound implements GameInterface {
    SoundPool soundPool;
    int Hit;

    public HitSound(Context context){
        //implements sound
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        Hit = soundPool.load(context,R.raw.tir,1);
    }

    public void play(){
        soundPool.play(Hit,1.0f,1.0f,1,0,1.0f);
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
