package com.example.weatherforecast.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

open class ForecastModel(
    @PrimaryKey var id: String = "",
    var cityName: String = "",
    var forecastDate: String = "",
    var temperature: Int = 0,
    var temperatureFeelsLike: Int = 0,
    var weatherStatus: String = "",
    var weatherImageUrl: String = ""
) : RealmObject()