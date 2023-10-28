package com.example.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
                sleep(5000);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                startActivities(new intent(splashActivity.this,MainActivity.class));
            }
        }
    };
    thread.start();
}
