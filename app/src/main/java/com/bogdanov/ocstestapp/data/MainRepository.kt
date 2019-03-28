package com.bogdanov.ocstestapp.data

import com.bogdanov.ocstestapp.data.api.Api
import com.bogdanov.ocstestapp.data.api.model.VacancyResponse
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(val apiService: Api) {
    fun getData(): Single<ArrayList<VacancyResponse>> = apiService.getData("android", 0)
}