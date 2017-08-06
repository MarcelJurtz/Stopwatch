package com.marceljurtz.simplestopwatch.countdown

import android.os.Handler
import com.marceljurtz.simplestopwatch.Presenter

class CountdownPresenter : Presenter<CountdownView> {

    private var countdownView: CountdownView? = null
    private var countdownModel: CountdownModel? = null
    private var startTime = 0L
    private var timeInSeconds = 0L
    private var timeSwapBuff = 0L
    private var updatedTime = 0L
    private var iterations = 0
    private var timerRunning = false
    private var secs = 0
    private var mins = 0
    private var hours  = 0
    private var handler = Handler()


    override fun attachView(view: CountdownView) {
        this.countdownView = view
        this.countdownModel = CountdownModel(countdownView?.applicationContext)
    }

    override fun detachView() {
        this.countdownView = null
    }

    fun initControls(): IntArray {
        return countdownModel?.getDefaultTime() ?: kotlin.IntArray(3)
    }

    fun setDefaultTime(seconds: Long) {
        countdownModel?.setDefaultTime(seconds)
    }

    fun startCountdown(seconds: Long) {
        // Save to SharedPreferences and start timer
        setDefaultTime(seconds)

        startTime = seconds
        handler.postDelayed(updateTimer, 1000)
    }

    fun stopCountdown() {
        timeSwapBuff += timeInSeconds
        handler.removeCallbacks(updateTimer)
    }

    fun resetCountdown() {
        reset()
    }

    // Update timer label
    private var updateTimer: Runnable = object : Runnable {
        override fun run() {
            iterations++
            timeInSeconds = startTime - 1 * iterations
            // updatedTime = timeSwapBuff + timeInSeconds
            secs = timeInSeconds.toInt()
            mins = secs / 60
            hours = mins / 60
            mins  = mins % 60
            secs = secs % 60
            if(secs < 0 || mins < 0 || hours < 0) {
                stopCountdown()
            } else {
                countdownView?.updateTimerView(hours, mins, secs)
                handler.postDelayed(this, 1000)
            }
        }
    }

    // Reset timer
    private fun reset() {
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
        countdownView?.updateTimerView(hours, mins, secs)
    }
}