package com.marceljurtz.simplestopwatch.countdown.Interfaces

interface IView {
    fun setCountdownText(hours: Int, minutes: Int, seconds: Int)
    fun enableControls()
    fun disableControls()
}