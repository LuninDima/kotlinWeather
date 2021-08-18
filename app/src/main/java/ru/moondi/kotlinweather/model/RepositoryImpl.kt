package ru.moondi.kotlinweather.model

import ru.moondi.kotlinweather.view.Weather

class RepositoryImpl: Repositrory {
    override fun getWeatherFromServer(): Weather {
        return  Weather()
    }

    override fun getWeatherFromLocale(): Weather {
       return Weather()
    }
}