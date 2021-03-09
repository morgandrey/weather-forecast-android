package com.example.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Andrey Morgunov on 09/03/2021.
 */

object Utils {
    fun getDateTime(s: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val netDate = Date(s * 1000)
        return sdf.format(netDate)
    }
}