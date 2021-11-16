package ru.moondi.kotlinweather.repository

import ru.moondi.kotlinweather.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}