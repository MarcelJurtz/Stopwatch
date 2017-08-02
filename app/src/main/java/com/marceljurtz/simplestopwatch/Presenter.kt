package com.marceljurtz.simplestopwatch

interface Presenter<V> {

    fun attachView(view: V)

    fun detachView()

}