package com.grouptwo.dyoga;

import androidx.appcompat.app.AppCompatActivity;

import androidx.os.Bundle;

public class SplashActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
    }
    Thread thread = new Thread() {
        @Override
        public void run() {

            try {
                sleep(3000);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                startActivities(new intent(splashActivity.this,MainActivity.class));
            }
        }
    };
    thread.start();
}