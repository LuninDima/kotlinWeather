package ru.moondi.kotlinweather.viewmodel

import ru.moondi.kotlinweather.model.Weather

sealed class AppStateMainFragment{
    data class Success(val dataWeather: List<Weather>): AppStateMainFragment()
    object Loading: AppStateMainFragment()
    data class Error(val error: Throwable) : AppStateMainFragment()
}

sealed class AppStateDetailsFragment {
    data class Success(val dataWeather: Weather) : AppStateDetailsFragment()
    object Loading : AppStateDetailsFragment()
    data class Error(val error: Throwable) : AppStateDetailsFragment()
}
