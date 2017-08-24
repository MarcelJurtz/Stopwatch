package com.marceljurtz.simplestopwatch.countdown.Helper

import android.content.Context
import com.marceljurtz.simplestopwatch.Helper.TimeInterval
import com.marceljurtz.simplestopwatch.R

class PreferenceManager(context: Context?) {
    val maxHours = 24;
    val maxMinutes = 59;
    val maxSeconds = 59;

    var appContext = context

    // Load default time from shared preferences
    public fun getDefaultTime() : TimeInterval {
        val shared = appContext?.getSharedPreferences(appContext?.getString(R.string.pref), Context.MODE_PRIVATE)
        val savedTime = shared?.getLong(appContext?.getString(R.string.pref_countdown_default),0) ?: 0

        var hours: Int = (savedTime / 3600).toInt()
        var minutes: Int = ((savedTime - (hours * 3600)) / 60).toInt()
        var seconds: Int = (savedTime % 60).toInt()

        val interval = TimeInterval(hours, minutes, seconds, 0)

        return interval
    }

    // Save default time to shared preferences
    fun setDefaultTime(seconds: Long) {
        val shared = appContext?.getSharedPreferences(appContext?.getString(R.string.pref), Context.MODE_PRIVATE)
        val editor = shared?.edit()
        editor?.putLong(appContext?.getString(R.string.pref_countdown_default), seconds)
        editor?.commit()
    }
}