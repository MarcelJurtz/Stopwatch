package com.marceljurtz.simplestopwatch.countdown

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.marceljurtz.simplestopwatch.Presenter
import com.marceljurtz.simplestopwatch.R
import com.marceljurtz.simplestopwatch.countdown.*
import kotlinx.android.synthetic.main.activity_countdown.*

class CountdownView : AppCompatActivity() {

    var presenter: CountdownPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up presenter
        presenter = CountdownPresenter()
        presenter?.attachView(this)

        setContentView(R.layout.activity_countdown)

        rlCountdownBackground.setOnClickListener {
            startTimer()
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

    protected fun onTimerUpdate(hours: Int, minutes: Int, seconds: Int) {
        lblCountdownHours.text = hours.toString()
        lblCountdownMinutes.text = minutes.toString()
        lblCountdownSeconds.text = seconds.toString()
    }

    protected fun startTimer() {
        cmdIncreaseHours.setVisibility(View.GONE)
        cmdDecreaseHours.setVisibility(View.GONE)
        cmdIncreaseMinutes.setVisibility(View.GONE)
        cmdDecreaseMinutes.setVisibility(View.GONE)
        cmdIncreaseSeconds.setVisibility(View.GONE)
        cmdDecreaseSeconds.setVisibility(View.GONE)

        var secondsMillis = lblCountdownSeconds.text.toString().toLong() * 1000
        var minutesMillis = lblCountdownMinutes.text.toString().toLong() * 60000
        var hoursMillis = lblCountdownHours.text.toString().toLong() * 3600000

        var milliseconds = hoursMillis + minutesMillis + secondsMillis

        Toast.makeText(applicationContext, "MS: " + milliseconds, Toast.LENGTH_SHORT).show()
        presenter?.startCountdown(milliseconds)
    }

    protected fun stopTimer() {
        cmdIncreaseHours.setVisibility(View.VISIBLE)
        cmdDecreaseHours.setVisibility(View.VISIBLE)
        cmdIncreaseMinutes.setVisibility(View.VISIBLE)
        cmdDecreaseMinutes.setVisibility(View.VISIBLE)
        cmdIncreaseSeconds.setVisibility(View.VISIBLE)
        cmdDecreaseSeconds.setVisibility(View.VISIBLE)
        presenter?.stopCountdown()
    }
}
