package com.dsmini.skillup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dsmini.skillup.Intro.IntroActivity;

public class SplashAcitivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivty);


        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(5*1000);

                    Intent i=new Intent(getBaseContext(), IntroActivity.class);
                    startActivity(i);

                    finish();
                } catch (Exception e) {

                }
            }
        };
        background.start();


    }
}