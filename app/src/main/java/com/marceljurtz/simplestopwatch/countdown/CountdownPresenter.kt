package com.marceljurtz.simplestopwatch.countdown

import android.os.Handler
import com.marceljurtz.simplestopwatch.Helper.TimeUnit
import com.marceljurtz.simplestopwatch.Helper.TimeInterval
import com.marceljurtz.simplestopwatch.countdown.Interfaces.IPresenter
import com.marceljurtz.simplestopwatch.countdown.Interfaces.IView

class CountdownPresenter : IPresenter {

    private var countdownView: IView? = null
    private var countdownModel: CountdownModel? = null

    private var startTime = 0L
    private var timeInSeconds = 0L
    private var timeInterval: TimeInterval? = null
    private var timeSwapBuff = 0L
    private var updatedTime = 0L
    private var iterations = 0
    private var timerRunning = false
    private var secs = 0
    private var mins = 0
    private var hours  = 0
    private var handler = Handler()


    // Presenter overrides
    override fun onCreate(view: IView) {
        // init model and presenter and set text
        this.countdownView = view
        this.countdownModel = CountdownModel()

        // TODO: SharedPreferences DefaultTime
    }
    override fun onDestroy() {}
    override fun onPause() {}
    override fun onResume() {}

    // GUI start & stop
    override fun startStopClick() {
        if(timerRunning) {
            // Stop Timer
            countdownView?.enableControls()
            stopTimer()
        } else {
            // Start Timer
            countdownView?.disableControls()
            startTimer()
        }
        timerRunning = !timerRunning
    }

    // GUI reset
    override fun resetClick() {
        countdownView?.setCountdownText(0,0,0)
        countdownView?.enableControls()
    }

    // Update time in model and view
    fun updateTime(unit: TimeUnit, amount: Int) {
        resetTimer()
        countdownModel?.updateTime(unit,amount)
        loadCountdownText()
    }

    // Set countdown text by value from model
    private fun loadCountdownText() {
        val timeInterval = countdownModel?.getTimeInTimeInterval() ?: TimeInterval(0, 0, 0, 0)
        onTimeChanged(timeInterval)
    }

    override fun onTimeChanged(timeInterval: TimeInterval?) {
        countdownView?.setCountdownText(timeInterval?.getHours() ?: 0, timeInterval?.getMinutes() ?: 0, timeInterval?.getSeconds() ?: 0)
    }


    // Timer related stuff
    fun startTimer() {
        // setDefaultTime(seconds)
        handler.postDelayed(updateTimer, 1000)
    }

    fun stopTimer() {
        timeSwapBuff += timeInSeconds
        handler.removeCallbacks(updateTimer)
    }

    fun resetTimer() {
        handler.removeCallbacks(updateTimer)
        startTime = 0L
        timeInSeconds = 0L
        timeSwapBuff = 0L
        updatedTime = 0L
        timerRunning = false
        secs = 0
        mins = 0
        hours = 0
        iterations = 0
    }

    // Countdown tick
    private var updateTimer: Runnable = object : Runnable {
        override fun run() {
            iterations++
            // timeInSeconds = startTime - 1 * iterations
            countdownModel?.tickBackwards()
            timeInSeconds = countdownModel?.getTimeInTimeInterval()?.getTimeInSeconds() ?: 0L
            // updatedTime = timeSwapBuff + timeInSeconds
            timeInterval = countdownModel?.getTimeInTimeInterval()
            secs = timeInterval?.getSeconds() ?: 0
            mins = timeInterval?.getMinutes() ?: 0
            hours = timeInterval?.getHours() ?: 0
            if(secs <= 0 && mins <= 0 && hours <= 0) {
                startStopClick()
            } else {
                // countdownView?.updateTimerView(hours, mins, secs)
                handler.postDelayed(this, 1000)
            }
            onTimeChanged(countdownModel?.getTimeInTimeInterval())
        }
    }
}