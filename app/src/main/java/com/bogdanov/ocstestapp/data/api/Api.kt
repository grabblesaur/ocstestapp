package com.bogdanov.ocstestapp.data.api

import com.bogdanov.ocstestapp.data.api.model.VacancyResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    companion object {
        const val POSITIONS = "positions.json"
    }

    @GET(POSITIONS)
    fun getData(@Query("search") search: String, @Query("page") page: Int): Observable<ArrayList<VacancyResponse>>

}