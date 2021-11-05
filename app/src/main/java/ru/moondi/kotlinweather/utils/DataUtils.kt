package ru.moondi.kotlinweather.utils

import ru.moondi.kotlinweather.model.FactDTO
import ru.moondi.kotlinweather.model.Weather
import ru.moondi.kotlinweather.model.WeatherDTO
import ru.moondi.kotlinweather.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feels_like!!, fact.condition!!))
}
