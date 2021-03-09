package com.example.weatherforecast.dto

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

data class Temperature(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val temperatureFeelsLike: Double
)