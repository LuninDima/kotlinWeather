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
    private val liveDataObserver: MutableLiveData<AppStateList> = MutableLiveData(),
    private val repositroryImpl: Repositrory = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataObserver
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    fun getWeatherFromRemotesource() = getDataFromLocalSource(isRussian = true)

    fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataObserver.value = AppStateList.Loading
        Thread {
            sleep(1000)
            liveDataObserver.postValue(
                AppStateList.Success(
                    if (isRussian) repositroryImpl.getWeatherFromLocalStorageRus()
                    else repositroryImpl.getWeatherFromLocalStorageWorld()
                )
            )
        }.start()
    }
}