package ru.moondi.kotlinweather.model

interface Repositrory {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocale(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}