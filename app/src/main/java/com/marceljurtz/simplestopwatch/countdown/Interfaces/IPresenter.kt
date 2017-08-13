package com.marceljurtz.simplestopwatch.countdown.Interfaces

import com.marceljurtz.simplestopwatch.Helper.TimeInterval

/*
    Definition of delegate methods for the default android activity lifecycle
    For testing, a mock based on this interface can be created
 */

interface IPresenter {
    fun onCreate(view: IView)
    fun onPause()
    fun onResume()
    fun onDestroy()
    fun onTimeChanged(timeInterval: TimeInterval?)

    fun startStopClick()
    fun resetClick()
}