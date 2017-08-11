package com.marceljurtz.simplestopwatch.countdown

/*
    Definition of delegate methods for the default android activity lifecycle
    For testing, a mock based on this interface can be created
 */

interface IPresenter {
    fun onCreate(view: IView)
    fun onPause()
    fun onResume()
    fun onDestroy()
    fun onTimeChanged(timeInterval: TimeInterval)
}