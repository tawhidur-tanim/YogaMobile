package com.grouptwo.dyoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.data.repositories.ClassInstanceRepository;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.interfaces.Callback;
import com.grouptwo.dyoga.utils.Constants;
import com.grouptwo.dyoga.utils.SharedPreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

public class GetStartedActivity extends AppCompatActivity {

    private ImageView screenLayout;
    private Button getStarted;

    private Handler handler;
    private boolean isSyncing = false;
    private int backgroundIndex = 0;
    private final int[] backgroundImages = {
            R.raw.splash_1,
            R.raw.splash_2
    };

    @Override
    protected void onStart() {
        super.onStart();

        if (isSyncing) return;

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            SharedPreferenceManager.saveData(this, Constants.USERNAME, currentUser.getUid());
            startActivity(new Intent(this, DashboardActivity.class));
            finishAfterTransition();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_get_started);

        screenLayout = findViewById(R.id.screenLayout);
        getStarted = findViewById(R.id.getStarted);

        handler = new Handler(Looper.getMainLooper());

        if (!obtainFirebaseUserAndSync()) {
            prepareBackgroundSwitcher();
        } else {
            findViewById(R.id.loadingProgressBar).setVisibility(View.VISIBLE);
        }

        getStarted.setOnClickListener(v -> startActivity(new Intent(this, AuthActivity.class)));
    }

    private void prepareBackgroundSwitcher() {
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> changeScreenBackground());
            }
        };
        timer.scheduleAtFixedRate(timerTask, 10000, 10000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> getStarted.setVisibility(View.VISIBLE));
            }
        }, 2000);
    }

    private void changeScreenBackground() {
        screenLayout.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        if (backgroundIndex == 0) {
            screenLayout.setImageResource(backgroundImages[++backgroundIndex]);
        } else {
            screenLayout.setImageResource(backgroundImages[--backgroundIndex]);
        }
        screenLayout.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }

    private boolean obtainFirebaseUserAndSync() {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            isSyncing = true;
            SharedPreferenceManager.saveData(this, Constants.USERNAME, currentUser.getUid());
            CourseRepository.sync(this, new Callback<Boolean>() {
                @Override
                public void onResult(Boolean result) {
                    syncNextData();
                }
            });

            return true;
        }

        return false;
    }

    private void syncNextData() {
        ClassInstanceRepository.sync(getApplication(), new Callback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finishAfterTransition();
            }
        });
    }
}