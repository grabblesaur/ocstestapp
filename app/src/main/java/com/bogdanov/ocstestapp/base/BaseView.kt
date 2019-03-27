package com.bogdanov.ocstestapp.base

interface BaseView {
    fun onError(message: String?)
    fun onError(throwable: Throwable?)
}
