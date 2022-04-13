package ru.moondi.kotlinweather.repository

import ru.moondi.kotlinweather.model.Weather
import ru.moondi.kotlinweather.room.HistoryDao
import ru.moondi.kotlinweather.utils.convertHistoryEntityToWeather
import ru.moondi.kotlinweather.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao): LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

}