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
        this.supportActionBar?.hide()

        // Set up presenter
        presenter = CountdownPresenter()
        presenter?.attachView(this)

        setContentView(R.layout.activity_countdown)

        rlCountdownBackground.setOnClickListener {
            if (!timerRunning) {
                startTimer()
            } else {
                stopTimer()
            }
        }

        rlCountdownBackground.setOnLongClickListener {
            if (timerRunning) {
                stopTimer()
            }
            presenter?.resetCountdown()
            true
        }

        cmdIncreaseHours.setOnClickListener {
            setTimerText(lblCountdownHours, 1, 24)
        }

        cmdIncreaseHours.setOnLongClickListener {
            setTimerText(lblCountdownHours, 10, 24)
            true
        }

        cmdDecreaseHours.setOnClickListener {
            setTimerText(lblCountdownHours, -1, 24)
        }

        cmdDecreaseHours.setOnLongClickListener {
            setTimerText(lblCountdownHours, -10, 24)
            true
        }

        cmdIncreaseMinutes.setOnClickListener {
            setTimerText(lblCountdownMinutes, 1, 59)
        }

        cmdIncreaseMinutes.setOnLongClickListener {
            setTimerText(lblCountdownMinutes, 10, 59)
            true
        }

        cmdDecreaseMinutes.setOnClickListener {
            setTimerText(lblCountdownMinutes, -1, 59)
        }

        cmdDecreaseMinutes.setOnLongClickListener {
            setTimerText(lblCountdownMinutes, -10, 59)
            true
        }

        cmdIncreaseSeconds.setOnClickListener {
            setTimerText(lblCountdownSeconds, 1, 59)
        }

        cmdIncreaseSeconds.setOnLongClickListener {
            setTimerText(lblCountdownSeconds, 10, 59)
            true
        }

        cmdDecreaseSeconds.setOnClickListener {
            setTimerText(lblCountdownSeconds, -1,59)
        }

        cmdDecreaseSeconds.setOnLongClickListener {
            setTimerText(lblCountdownSeconds, -10, 59)
            true
        }

        cmdSwitchToTimer.setOnClickListener {
            var intent = Intent(applicationContext, TimerActivity::class.java)
            finish()
            startActivity(intent)
        }

        initCountdownView()

    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    private fun initCountdownView() {
        var time: IntArray = presenter?.initControls() ?: kotlin.IntArray(3)
        var seconds = time[2]
        var minutes = time[1]
        var hours = time [0]

        updateTimerView(hours, minutes, seconds)
    }

    fun setTimerText(view: TextView, change: Int, max: Int) {
        var time = view.text.toString().toInt() + change
        if (time >= 0 && time <= max) {
            if (time < 10) {
                view.text = "0" + time.toString()
            } else {
                view.text = time.toString()
            }
        } else if (time >= 0) {
            view.text = "00"
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

        if (combinedSeconds > 0) {
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
