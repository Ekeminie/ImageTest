package com.example.android.imagetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_splash);
        Thread newThread = new Thread(){
            @Override
            public void run (){
                try {
                    sleep(3000);
                    Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(newIntent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }; newThread.start();
    }
}
