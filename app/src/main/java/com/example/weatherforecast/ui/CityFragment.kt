package com.example.weatherforecast.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentCityBinding
import com.example.weatherforecast.dto.Forecast
import com.example.weatherforecast.models.ForecastModel
import com.example.weatherforecast.presentation.CityFragmentAdapter
import com.example.weatherforecast.presentation.CityFragmentContract
import com.example.weatherforecast.presentation.CityFragmentPresenter
import com.example.weatherforecast.utils.Utils.getDateTime
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt


class CityFragment : Fragment(R.layout.fragment_city), CityFragmentContract.View {

    private val binding: FragmentCityBinding by viewBinding()
    private val API_ICON: String = "https://openweathermap.org/img/w/"
    private lateinit var cityFragmentPresenter: CityFragmentPresenter
    private lateinit var adapter: CityFragmentAdapter
    private var list = mutableListOf<ForecastModel>()
    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityFragmentPresenter = CityFragmentPresenter(this)

        realm = Realm.getDefaultInstance()
        reloadViewPager()

        binding.addNewCityButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Title")
                .setItems(R.array.cities) { dialog, id ->
                    val city = (dialog as AlertDialog).listView.getItemAtPosition(id) as String
                    realm.beginTransaction()
                    val forecastModel =
                        realm.createObject(ForecastModel::class.java, UUID.randomUUID().toString())
                    forecastModel.cityName = city
                    realm.commitTransaction()
                    GlobalScope.launch {
                        cityFragmentPresenter.getCityForecast(city)
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
        binding.refreshButton.setOnClickListener {
            with(binding) {
                progressBar.max = list.size
                progressBar.progress = 0
            }
            for (i in list.indices) {
                cityFragmentPresenter.getCityForecast(list[i].cityName)
            }
        }
    }

    private fun reloadViewPager() {
        list = realm.where<ForecastModel>().findAll()
        adapter = CityFragmentAdapter(list)
        binding.viewPager.adapter = adapter
        binding.indicator.attachToPager(binding.viewPager)
    }

    override fun onSuccessForecast(forecast: Forecast) {
        val forecastModel = ForecastModel(
            cityName = forecast.cityName,
            forecastDate = getDateTime(forecast.forecastDate),
            temperature = forecast.temperature.temperature.roundToInt(),
            temperatureFeelsLike = forecast.temperature.temperatureFeelsLike.roundToInt(),
            weatherStatus = forecast.weatherInfo[0].weatherStatus,
            weatherImageUrl = "$API_ICON${forecast.weatherInfo[0].weatherImage}.png"
        )
        realm.beginTransaction()
        val model = realm.where<ForecastModel>()
            .equalTo("cityName", forecastModel.cityName)
            .findFirst()!!
        with(model) {
            this.forecastDate = forecastModel.forecastDate
            this.temperature = forecastModel.temperature
            this.temperatureFeelsLike = forecastModel.temperatureFeelsLike
            this.weatherStatus = forecastModel.weatherStatus
            this.weatherImageUrl = forecastModel.weatherImageUrl
        }
        realm.copyToRealmOrUpdate(model)
        realm.commitTransaction()
        binding.progressBar.progress++
        reloadViewPager()
    }

    override fun onError(e: String) {
        Toast.makeText(requireContext(), e, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cityFragmentPresenter.onDestroy()
    }
}