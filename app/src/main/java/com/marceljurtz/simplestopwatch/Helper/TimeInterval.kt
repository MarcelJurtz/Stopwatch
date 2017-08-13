package com.marceljurtz.simplestopwatch.Helper

class TimeInterval(hours: Int, minutes: Int, seconds: Int, milliseconds: Int) {
    private var IntervalHours = hours
    private var IntervalMinutes = minutes
    private var IntervalSeconds = seconds
    private var IntervalMilliseconds = milliseconds

    fun getHours() : Int {
        return IntervalHours
    }

    fun getMinutes() : Int {
        return IntervalMinutes
    }

    fun getSeconds() : Int {
        return IntervalSeconds
    }

    fun getMilliseconds() : Int {
        return IntervalMilliseconds
    }

    fun getTimeInSeconds() : Long {
        val seconds: Long =  (IntervalHours * 60 * 60 + IntervalMinutes * 60 + IntervalSeconds + Math.floor(IntervalMilliseconds.toDouble() / 1000)).toLong()
        return seconds
    }
}
