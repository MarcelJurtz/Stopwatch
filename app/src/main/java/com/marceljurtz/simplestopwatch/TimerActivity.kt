package com.marceljurtz.simplestopwatch

import android.os.Handler
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_timer.*

class TimerActivity : AppCompatActivity() {


    // Declarations
    var startTime = 0L
    var timeInMilliseconds = 0L
    var timeSwapBuff = 0L
    var updatedTime = 0L
    var timerRunning = false
    var millis = 0
    var secs = 0
    var mins = 0


    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        this.supportActionBar!!.hide()

        rlBackground.setOnClickListener {
            if (!timerRunning) {
                // Start timer
                startTime = SystemClock.uptimeMillis()
                handler.postDelayed(updateTimer, 0)
                timerRunning = true
            } else {
                // Stop timer (pause)
                timeSwapBuff += timeInMilliseconds
                handler.removeCallbacks(updateTimer)
                timerRunning = false
            }
        }
        rlBackground.setOnLongClickListener {
            // Reset timer
            reset()
            true
        }
    }

    // Update timer label
    var updateTimer: Runnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            updatedTime = timeSwapBuff + timeInMilliseconds
            secs = (updatedTime / 1000).toInt()
            mins = secs / 60
            secs = secs % 60
            millis = (updatedTime % 1000).toInt()
            txtTime.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", millis))
            handler.postDelayed(this, 0)
        }
    }

    // Reset timer
    private fun reset() {
        startTime = 0L
        timeInMilliseconds = 0L
        timeSwapBuff = 0L
        updatedTime = 0L
        timerRunning = false
        secs = 0
        mins = 0
        millis = 0
        handler.removeCallbacks(updateTimer)
        txtTime.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", millis))
    }

    override fun onDestroy() {
        reset()
        super.onDestroy()
    }
}
