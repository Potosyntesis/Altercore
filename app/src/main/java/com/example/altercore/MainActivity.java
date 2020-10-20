package com.example.altercore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ImageButton play = findViewById(R.id.play);
        ImageButton credit = findViewById(R.id.credit);
        ImageButton exit = findViewById(R.id.exit);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartGame();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    public void StartGame(){
        Intent play = new Intent(this,GameActivity.class);
        startActivity(play);
    }
}
