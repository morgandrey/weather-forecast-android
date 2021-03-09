package com.example.weatherforecast.presentation

import com.example.weatherforecast.remote.ForecastService
import com.example.weatherforecast.remote.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

class CityFragmentPresenter(private val view: CityFragmentContract.View) :
    CityFragmentContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var forecastService: ForecastService

    override fun getCityForecast(city: String) {
        forecastService = NetworkService.forecastService
        compositeDisposable.add(
            forecastService.getCityForecast(city, "d584d9cff6427aeff3374d70a7faa152")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { forecast -> view.onSuccessForecast(forecast) },
                    { error -> view.onError(error.toString()) }
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}