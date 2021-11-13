package com.thejan.starwars.network

import com.thejan.starwars.model.Planet
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Thejan Thrimanna on 11/12/21.
 */
interface ApiInterface {
    @GET("planets")
    fun getPlanets(
        @Query("page") page: Int
    ): Observable<ServiceResponse<List<Planet>>>
}