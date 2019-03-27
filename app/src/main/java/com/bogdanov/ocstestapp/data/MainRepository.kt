package com.bogdanov.ocstestapp.data

import com.bogdanov.ocstestapp.data.api.Api
import com.bogdanov.ocstestapp.data.api.model.Vacancy
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(val apiService: Api) {
    fun getData(): Single<ArrayList<Vacancy>> = apiService.getData("android", 0)
}