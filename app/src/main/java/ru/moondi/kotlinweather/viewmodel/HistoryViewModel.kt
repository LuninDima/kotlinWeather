package ru.moondi.kotlinweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.moondi.kotlinweather.app.App.Companion.getHistoryDao
import ru.moondi.kotlinweather.repository.LocalRepository
import ru.moondi.kotlinweather.repository.LocalRepositoryImpl

class HistoryViewModel(
    private val historyLiveData: MutableLiveData<AppStateMainFragment> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppStateMainFragment.Loading
        historyLiveData.value = AppStateMainFragment.Success(historyRepository.getAllHistory())
    }

}
