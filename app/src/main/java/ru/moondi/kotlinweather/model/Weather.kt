package ru.moondi.kotlinweather.view

class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 20,
    val feelsLike: Int = 21
)

fun getDefaultCity() = City("Москва", 55.0, 53.0)

data class City(
    val name: String,
    val lat: Double,
    val long: Double,
)
