package ru.moondi.kotlinweather.repository

import ru.moondi.kotlinweather.model.Weather
import ru.moondi.kotlinweather.room.HistoryDao

class LocalRepositoryImpl(private val localDataSource: HistoryDao): LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

}