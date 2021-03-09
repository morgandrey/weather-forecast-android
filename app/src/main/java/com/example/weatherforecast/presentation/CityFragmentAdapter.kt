package com.example.weatherforecast.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.models.ForecastModel


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

class CityFragmentAdapter(private var dataSet: List<ForecastModel>) :
    RecyclerView.Adapter<CityFragmentAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cityName = itemView.findViewById<TextView>(R.id.city_name_text_view)
        private val forecastDate = itemView.findViewById<TextView>(R.id.forecast_date_text_view)
        private val temperature =
            itemView.findViewById<TextView>(R.id.forecast_temperature_text_view)
        private val temperatureFeelsLike =
            itemView.findViewById<TextView>(R.id.temperature_feels_like_text_view)
        private val weatherStatus = itemView.findViewById<TextView>(R.id.weather_status_text_view)
        private val weatherImage = itemView.findViewById<ImageView>(R.id.weather_image_view)

        fun bind(item: ForecastModel) {
            cityName.text = item.cityName
            forecastDate.text = item.forecastDate
            temperature.text = itemView.context.getString(
                R.string.temperature,
                item.temperature.toString()
            )
            temperatureFeelsLike.text = itemView.context.getString(
                R.string.fells_like,
                item.temperatureFeelsLike.toString()
            )
            weatherStatus.text = item.weatherStatus
            Glide.with(itemView)
                .load(item.weatherImageUrl)
                .centerCrop()
                .into(weatherImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.city_item_view, parent, false)
                return ViewHolder(view)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = dataSet.size
}