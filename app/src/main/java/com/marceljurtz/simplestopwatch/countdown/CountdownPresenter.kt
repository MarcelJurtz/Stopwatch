package com.marceljurtz.simplestopwatch.countdown

import com.marceljurtz.simplestopwatch.Presenter

class CountdownPresenter : Presenter<CountdownView> {

    private var countdownView: CountdownView? = null


    override fun attachView(view: CountdownView) {
        this.countdownView = view
    }

    override fun detachView() {
        this.countdownView = null
    }

    fun startCountdown(timeInMilliseconds: Long) {
        // Save to SharedPreferences and start timer

    }

    fun stopCountdown() {

    }
}