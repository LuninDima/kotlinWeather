package ru.moondi.kotlinweather.viewmodel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.moondi.kotlinweather.model.RepositoryImpl
import ru.moondi.kotlinweather.model.Repositrory
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(
    private val liveDataObserver: MutableLiveData<AppState> = MutableLiveData(),
    val repositrory: Repositrory = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataObserver
    fun getWeather() = getDataFromLocalSource()

    fun getDataFromLocalSource() {
        val rand: Int = Random.nextInt(0, 3)
        when (rand) {
            0 -> {
                TODO("error")
            }
            1 -> {
                liveDataObserver.postValue(AppState.Loading)
            }
            2 -> {
                liveDataObserver.postValue(AppState.Succes(repositrory.getWeatherFromLocale()))
            }
        }
    }
}