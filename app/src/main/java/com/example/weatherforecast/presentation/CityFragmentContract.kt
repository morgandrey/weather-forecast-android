package com.example.weatherforecast.presentation

import com.example.weatherforecast.dto.Forecast


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

interface CityFragmentContract {
    interface View {
        fun onSuccessForecast(forecast: Forecast)
        fun onError(e: String)
    }

    interface Presenter {
        fun getCityForecast(city: String)
        fun onDestroy()
    }
}