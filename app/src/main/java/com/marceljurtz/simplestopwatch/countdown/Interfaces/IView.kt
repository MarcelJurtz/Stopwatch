package com.marceljurtz.simplestopwatch.countdown.Interfaces

import android.content.Context

interface IView {
    fun setSecondsText(text: String)
    fun setMinutesText(text: String)
    fun setHoursText(text: String)

    fun enableControls()
    fun disableControls()

    fun getContext() : Context
}