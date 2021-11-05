package ru.moondi.kotlinweather.repository

import okhttp3.Callback


interface DetailsRepository {
    fun getWeatherDetailsFromServer(requestLink: String, callback: Callback)
}