package ru.moondi.kotlinweather.model

class RepositoryImpl : Repositrory {
    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocale() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}