package com.marceljurtz.simplestopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {


    // Deklarationen
    RelativeLayout backgroundLayout;
    TextView txtTime;
    long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    boolean timerRunning = false;
    int millis = 0;
    int secs = 0;
    int mins = 0;
    int hours = 0;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        this.getSupportActionBar().hide();

        backgroundLayout = (RelativeLayout)findViewById(R.id.rlBackground);
        txtTime = (TextView)findViewById(R.id.txtTime);


        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timerRunning)
                {
                    // Starte Timer
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    timerRunning = true;
                } else {
                    // Stoppe Timer
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    timerRunning = false;
                }
            }
        });
        backgroundLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Timer resetten
                reset();
                return true;
            }
        });
    }
    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int)(updatedTime/1000);
            mins = secs / 60;
            secs = secs % 60;
            millis = (int)(updatedTime % 1000);
            txtTime.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", millis));
            handler.postDelayed(this,0);
        }
    };

    private void reset() {
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        timerRunning = false;
        secs = 0;
        mins = 0;
        millis = 0;
        handler.removeCallbacks(updateTimer);
        txtTime.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", millis));
    }

    @Override
    protected void onDestroy() {
        reset();
        super.onDestroy();
    }
}
