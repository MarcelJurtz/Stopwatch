package com.marceljurtz.simplestopwatch.countdown

import android.os.Handler
import com.marceljurtz.simplestopwatch.TimeUnit

class CountdownPresenter : IPresenter {

    private var countdownView: IView? = null
    private var countdownModel: Countdown? = null

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
        this.countdownModel = Countdown()
    }
    override fun onDestroy() {}
    override fun onPause() {}
    override fun onResume() {}


    fun updateTime(unit: TimeUnit, amount: Int) {
        countdownModel?.updateTime(unit,amount)
        loadCountdownText()
    }

    fun startCountdownClick(hours: Int, Minutes: Int, seconds: Int) {
        // Save to SharedPreferences and start timer
        startTimer()
    }

    fun stopCountdown() {
        stopTimer()
    }

    fun resetCountdown() {
        resetTimer()
        loadCountdownText()
    }

    private fun loadCountdownText() {
        val timeInterval = countdownModel?.getTimeInTimeInterval() ?: TimeInterval(0,0,0,0)
        countdownView?.setCountdownText(timeInterval.getHours(), timeInterval.getMinutes(), timeInterval.getSeconds())
    }

    override fun onTimeChanged(timeInterval: TimeInterval) {
        countdownView?.setCountdownText(timeInterval.getHours(), timeInterval.getMinutes(), timeInterval.getSeconds())
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
        startTime = 0L
        timeInSeconds = 0L
        timeSwapBuff = 0L
        updatedTime = 0L
        timerRunning = false
        secs = 0
        mins = 0
        hours = 0
        iterations = 0
        handler.removeCallbacks(updateTimer)
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
            if(secs < 0 || mins < 0 || hours < 0) {
                stopTimer()
            } else {
                // countdownView?.updateTimerView(hours, mins, secs)
                handler.postDelayed(this, 1000)
            }
        }
    }
}