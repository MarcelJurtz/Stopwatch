package com.marceljurtz.simplestopwatch.countdown

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.marceljurtz.simplestopwatch.Presenter
import com.marceljurtz.simplestopwatch.R
import com.marceljurtz.simplestopwatch.countdown.*
import com.marceljurtz.simplestopwatch.stopwatch.TimerActivity
import kotlinx.android.synthetic.main.activity_countdown.*

class CountdownView : AppCompatActivity() {

    var presenter: CountdownPresenter? = null
    var timerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up presenter
        presenter = CountdownPresenter()
        presenter?.attachView(this)

        setContentView(R.layout.activity_countdown)

        rlCountdownBackground.setOnClickListener {
            if(!timerRunning) {
                startTimer()
            } else {
                stopTimer()
            }
        }

        rlCountdownBackground.setOnLongClickListener {
            if(timerRunning) {
                stopTimer()
            }
            presenter?.resetCountdown()
            true
        }

        cmdIncreaseHours.setOnClickListener {
            setTimerText(lblCountdownHours, 1)
        }

        cmdDecreaseHours.setOnClickListener {
            setTimerText(lblCountdownHours, -1)
        }

        cmdIncreaseMinutes.setOnClickListener {
            setTimerText(lblCountdownMinutes, 1)
        }

        cmdDecreaseMinutes.setOnClickListener {
            setTimerText(lblCountdownMinutes, -1)
        }

        cmdIncreaseSeconds.setOnClickListener {
            setTimerText(lblCountdownSeconds, 1)
        }

        cmdDecreaseSeconds.setOnClickListener {
            setTimerText(lblCountdownSeconds, -1)
        }
        cmdSwitchToTimer.setOnClickListener {
            var intent = Intent(applicationContext, TimerActivity::class.java)
            finish()
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    fun setTimerText(view: TextView, change: Int) {
        var time = view.text.toString().toInt() + change
        if(time >= 0) {
            if(time < 10 ) {
                view.text = "0" + time.toString()
            } else {
                view.text = time.toString()
            }
        }
    }

    public fun updateTimerView(hours: Int, minutes: Int, seconds: Int) {
        lblCountdownHours.text = (String.format("%02d", hours))
        lblCountdownMinutes.text = (String.format("%02d", minutes))
        lblCountdownSeconds.text = (String.format("%02d", seconds))
    }

    protected fun startTimer() {
        var seconds = lblCountdownSeconds.text.toString().toLong()
        var minutesInSeconds = lblCountdownMinutes.text.toString().toLong() * 60
        var hoursInSeconds = lblCountdownHours.text.toString().toLong() * 3600

        var combinedSeconds = seconds + minutesInSeconds + hoursInSeconds

        if(combinedSeconds > 0) {
            cmdIncreaseHours.setVisibility(View.GONE)
            cmdDecreaseHours.setVisibility(View.GONE)
            cmdIncreaseMinutes.setVisibility(View.GONE)
            cmdDecreaseMinutes.setVisibility(View.GONE)
            cmdIncreaseSeconds.setVisibility(View.GONE)
            cmdDecreaseSeconds.setVisibility(View.GONE)

            cmdSwitchToTimer.setVisibility(View.GONE)

            timerRunning = true
            presenter?.startCountdown(combinedSeconds)
        }
    }

    protected fun stopTimer() {
        cmdIncreaseHours.setVisibility(View.VISIBLE)
        cmdDecreaseHours.setVisibility(View.VISIBLE)
        cmdIncreaseMinutes.setVisibility(View.VISIBLE)
        cmdDecreaseMinutes.setVisibility(View.VISIBLE)
        cmdIncreaseSeconds.setVisibility(View.VISIBLE)
        cmdDecreaseSeconds.setVisibility(View.VISIBLE)

        cmdSwitchToTimer.setVisibility(View.VISIBLE)

        presenter?.stopCountdown()
        timerRunning = false
    }
}
