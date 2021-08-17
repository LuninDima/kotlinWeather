package ru.moondi.kotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel (private val liveDataObserver : MutableLiveData<Any> = MutableLiveData()) : ViewModel() {
    fun getLiveData()= liveDataObserver
    }