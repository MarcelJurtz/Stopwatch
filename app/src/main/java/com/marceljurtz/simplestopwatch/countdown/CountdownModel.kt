package com.marceljurtz.simplestopwatch.countdown

import android.content.Context
import com.marceljurtz.simplestopwatch.R

class CountdownModel(context: Context?) {

    val maxHours = 24;
    val maxMinutes = 59;
    val maxSeconds = 59;

    var appContext = context

    // Load default time from shared preferences
    public fun getDefaultTime() : IntArray {
        val shared = appContext?.getSharedPreferences(appContext?.getString(R.string.pref), Context.MODE_PRIVATE)
        val savedTime = shared?.getLong(appContext?.getString(R.string.pref_countdown_default),0) ?: 0

        var hours: Long = savedTime / 3600
        var minutes: Long = (savedTime - (hours * 3600)) / 60
        var seconds: Long = savedTime % 60

        var arr = IntArray(3)
        arr.set(0, seconds.toInt())
        arr.set(1, minutes.toInt())
        arr.set(2, hours.toInt())

        return arr
    }

    // Save default time to shared preferences
    public fun setDefaultTime(seconds: Long) {
        val shared = appContext?.getSharedPreferences(appContext?.getString(R.string.pref), Context.MODE_PRIVATE)
        val editor = shared?.edit()
        editor?.putLong(appContext?.getString(R.string.pref_countdown_default), seconds)
        editor?.commit()
    }

}