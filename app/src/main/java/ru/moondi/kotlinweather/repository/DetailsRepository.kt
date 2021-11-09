package ru.moondi.kotlinweather.repository

import okhttp3.Callback
import ru.moondi.kotlinweather.model.WeatherDTO


interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}