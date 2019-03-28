package com.bogdanov.ocstestapp.ui.main

import com.bogdanov.ocstestapp.base.BaseView
import com.bogdanov.ocstestapp.domain.Vacancy

interface MainView : BaseView {
    fun showCandidates(vacancyList: MutableList<Vacancy>)
}