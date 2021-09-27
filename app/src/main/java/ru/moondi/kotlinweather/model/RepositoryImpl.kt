package ru.moondi.kotlinweather.model

import ru.moondi.kotlinweather.view.Weather
import ru.moondi.kotlinweather.view.getRussianCities
import ru.moondi.kotlinweather.view.getWorldCities

class RepositoryImpl : Repositrory {
    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocale() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}