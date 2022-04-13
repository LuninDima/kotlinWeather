package ru.moondi.kotlinweather.viewmodel

import ru.moondi.kotlinweather.model.Weather

sealed class AppStateList {
    data class Success(val dataWeather: List<Weather>) : AppStateList()
    data class Error(val error: Throwable) : AppStateList()
    object Loading : AppStateList()
}

sealed class AppStateDetailsFragment {
    data class Success(val dataWeather: Weather) : AppStateDetailsFragment()
    object Loading : AppStateDetailsFragment()
    data class Error(val error: Throwable) : AppStateDetailsFragment()
}
