package com.example.weatherforecast.dto

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

data class WeatherInfo(
    @SerializedName("main")
    val weatherStatus: String,
    @SerializedName("icon")
    val weatherImage: String
)