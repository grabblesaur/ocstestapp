package com.bogdanov.ocstestapp.ui.main

import com.bogdanov.ocstestapp.base.BaseView
import com.bogdanov.ocstestapp.data.api.model.Vacancy

interface MainView : BaseView {
    fun showCandidates(vacancies: ArrayList<Vacancy>)
    fun setProgressBar(flag: Boolean)
}