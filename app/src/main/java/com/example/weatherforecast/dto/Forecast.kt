package com.example.weatherforecast.dto

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

data class Forecast(
    @SerializedName("name")
    val cityName: String,
    @SerializedName("dt")
    val forecastDate: Long,
    @SerializedName("main")
    val temperature: Temperature,
    @SerializedName("weather")
    val weatherInfo: List<WeatherInfo>
)