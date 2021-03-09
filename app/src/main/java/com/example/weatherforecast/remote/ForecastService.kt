package com.example.weatherforecast.remote

import com.example.weatherforecast.dto.Forecast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

interface ForecastService {
    @GET("weather?units=metric")
    fun getCityForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Observable<Forecast>

}