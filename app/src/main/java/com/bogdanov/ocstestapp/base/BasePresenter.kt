package com.bogdanov.ocstestapp.base

interface BasePresenter<in T : BaseView> {

    fun takeView(t: T)

    fun detachFromView()
}
