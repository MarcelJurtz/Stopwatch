package com.marceljurtz.simplestopwatch.countdown

import android.os.Handler
import com.marceljurtz.simplestopwatch.TimeUnit

class Countdown {

    private var timeInSeconds = 0L
    private var timeInterval = TimeInterval(0,0,0,0)


    fun getTimeInTimeInterval(): TimeInterval {
        return getTimeIntervalFromSeconds(timeInSeconds)
    }

    // Use this to increase or decrease the time
    fun updateTime(unit: TimeUnit, amount: Int) {
        val timeInSecondsBk = timeInSeconds

        if(unit.equals(TimeUnit.HOUR)) {
            timeInSeconds += amount * 60 * 60
        } else if(unit.equals(TimeUnit.MINUTE)) {
            timeInSeconds += amount * 60
        } else if(unit.equals(TimeUnit.SECOND)) {
            timeInSeconds += amount
        }

        if(timeInSeconds < 0) timeInSeconds = timeInSecondsBk
    }

    fun getTimeIntervalFromSeconds(seconds: Long): TimeInterval {
        var mSeconds = seconds.toInt()
        var mMinutes = mSeconds / 60
        var mHours = mMinutes / 60
        mMinutes  = mMinutes % 60
        mSeconds = mSeconds % 60

        return TimeInterval(mHours, mMinutes, mSeconds, 0)
    }

    fun tickBackwards() {
        updateTime(TimeUnit.SECOND, -1)
    }
}