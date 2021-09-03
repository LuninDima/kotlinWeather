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
    private val repositroryImpl: Repositrory = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataObserver
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataObserver.value = AppState.Loading
        Thread {
            sleep(5000)
            liveDataObserver.postValue(
                AppState.Succes(
                    if (isRussian) repositroryImpl.getWeatherFromLocalStorageRus()
                    else repositroryImpl.getWeatherFromLocalStorageWorld()
                )
            )
        }.start()
    }
}