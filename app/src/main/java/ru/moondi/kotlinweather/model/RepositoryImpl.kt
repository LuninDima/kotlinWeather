package ru.moondi.kotlinweather.model

import ru.moondi.kotlinweather.view.Weather
import ru.moondi.kotlinweather.view.getRussianCities
import ru.moondi.kotlinweather.view.getWorldCities

class RepositoryImpl : Repositrory {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocale(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather>{
        return getWorldCities()
    }
}