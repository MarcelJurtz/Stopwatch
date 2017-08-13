package com.marceljurtz.simplestopwatch.countdown

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.marceljurtz.simplestopwatch.R
import com.marceljurtz.simplestopwatch.Helper.TimeUnit
import com.marceljurtz.simplestopwatch.countdown.Interfaces.IView
import com.marceljurtz.simplestopwatch.stopwatch.TimerActivity
import kotlinx.android.synthetic.main.activity_countdown.*

class CountdownView : AppCompatActivity(), IView {

    var presenter: CountdownPresenter? = null
    var timerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        // Set up presenter
        presenter = CountdownPresenter()
        presenter?.onCreate(this)

        setContentView(R.layout.activity_countdown)

        rlCountdownBackground.setOnClickListener {
            presenter?.startStopClick()
        }

        rlCountdownBackground.setOnLongClickListener {
            presenter?.resetClick()
            true
        }

        // Handle Hour Button Clicks
        cmdIncreaseHours.setOnClickListener {
            presenter?.updateTime(TimeUnit.HOUR, 1)
        }

        cmdIncreaseHours.setOnLongClickListener {
            presenter?.updateTime(TimeUnit.HOUR, 10)
            true
        }

        cmdDecreaseHours.setOnClickListener {
            presenter?.updateTime(TimeUnit.HOUR, -1)
        }

        cmdDecreaseHours.setOnLongClickListener {
            presenter?.updateTime(TimeUnit.HOUR, -10)
            true
        }

        // Handle Minute Button Clicks
        cmdIncreaseMinutes.setOnClickListener {
            presenter?.updateTime(TimeUnit.MINUTE, 1)
        }

        cmdIncreaseMinutes.setOnLongClickListener {
            presenter?.updateTime(TimeUnit.MINUTE, 10)
            true
        }

        cmdDecreaseMinutes.setOnClickListener {
            presenter?.updateTime(TimeUnit.MINUTE, -1)
        }

        cmdDecreaseMinutes.setOnLongClickListener {
            presenter?.updateTime(TimeUnit.MINUTE, -10)
            true
        }

        // Handle Second Button Clicks
        cmdIncreaseSeconds.setOnClickListener {
            presenter?.updateTime(TimeUnit.SECOND, 1)
        }

        cmdIncreaseSeconds.setOnLongClickListener {
            presenter?.updateTime(TimeUnit.SECOND, 10)
            true
        }

        cmdDecreaseSeconds.setOnClickListener {
            presenter?.updateTime(TimeUnit.SECOND, -1)
        }

        cmdDecreaseSeconds.setOnLongClickListener {
            presenter?.updateTime(TimeUnit.SECOND, -10)
            true
        }

        // Handle Activity Switch Button Clicks
        cmdSwitchToTimer.setOnClickListener {
            var intent = Intent(applicationContext, TimerActivity::class.java)
            presenter?.onDestroy() // ?
            finish()
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun setCountdownText(hours: Int, minutes: Int, seconds: Int) {
        lblCountdownHours.text = (String.format("%02d", hours))
        lblCountdownMinutes.text = (String.format("%02d", minutes))
        lblCountdownSeconds.text = (String.format("%02d", seconds))
    }

    override fun disableControls() {
        cmdIncreaseHours.setVisibility(View.GONE)
        cmdDecreaseHours.setVisibility(View.GONE)
        cmdIncreaseMinutes.setVisibility(View.GONE)
        cmdDecreaseMinutes.setVisibility(View.GONE)
        cmdIncreaseSeconds.setVisibility(View.GONE)
        cmdDecreaseSeconds.setVisibility(View.GONE)
        cmdSwitchToTimer.setVisibility(View.GONE)
    }

    override fun enableControls() {
        cmdIncreaseHours.setVisibility(View.VISIBLE)
        cmdDecreaseHours.setVisibility(View.VISIBLE)
        cmdIncreaseMinutes.setVisibility(View.VISIBLE)
        cmdDecreaseMinutes.setVisibility(View.VISIBLE)
        cmdIncreaseSeconds.setVisibility(View.VISIBLE)
        cmdDecreaseSeconds.setVisibility(View.VISIBLE)
        cmdSwitchToTimer.setVisibility(View.VISIBLE)
    }
}
