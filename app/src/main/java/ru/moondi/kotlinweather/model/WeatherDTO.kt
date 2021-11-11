package ru.moondi.kotlinweather.model

import android.media.Image

data class WeatherDTO(
    val fact: FactDTO?
)
data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?,
    val icon: String?
)