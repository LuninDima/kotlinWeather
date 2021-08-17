package ru.moondi.kotlinweather.viewmodel

sealed class AppState{
    data class Succes(val dataWeather: Any):AppState()
    data class Error(val error:Throwable):AppState()
    object  Loading: AppState()
}
