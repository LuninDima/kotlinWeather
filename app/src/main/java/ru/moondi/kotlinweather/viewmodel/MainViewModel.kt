package ru.moondi.kotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.moondi.kotlinweather.model.RepositoryImpl
import ru.moondi.kotlinweather.model.Repositrory
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataObserver: MutableLiveData<AppState> = MutableLiveData(),
    val repositrory: Repositrory = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataObserver
    fun getWeather() = getDataFromLocalSource()

    fun getDataFromLocalSource() {
        Thread {
            liveDataObserver.postValue(AppState.Loading)
            sleep(4000)
            liveDataObserver.postValue(AppState.Succes(repositrory.getWeatherFromLocale()))
        }.start()
    }
}