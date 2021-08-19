package ru.moondi.kotlinweather.viewmodel

import ru.moondi.kotlinweather.view.Weather

sealed class AppState {
    data class Succes(val dataWeather: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
