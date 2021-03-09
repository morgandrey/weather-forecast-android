package com.example.weatherforecast.remote


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

object NetworkService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val forecastService: ForecastService
        get() = RetrofitClient.getClient(BASE_URL).create(ForecastService::class.java)
}