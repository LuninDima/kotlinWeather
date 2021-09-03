package ru.moondi.kotlinweather.model

import ru.moondi.kotlinweather.view.Weather

interface Repositrory {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocale(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}