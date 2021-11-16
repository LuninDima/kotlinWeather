package ru.moondi.kotlinweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.moondi.kotlinweather.app.App.Companion.getHistoryDao
import ru.moondi.kotlinweather.model.Weather
import ru.moondi.kotlinweather.model.WeatherDTO
import ru.moondi.kotlinweather.repository.*
import java.io.IOException
import ru.moondi.kotlinweather.utils.convertDtoToModel
import java.nio.file.WatchEvent

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
     val detailsLiveData: MutableLiveData<AppStateDetailsFragment> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
     private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveData.value = AppStateDetailsFragment.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callBack)
    }

    fun saveCityToDB(weather: Weather){
        historyRepository.saveEntity(weather)
    }

    private val callBack = object : Callback<WeatherDTO> {

        @Throws(IOException::class)
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppStateDetailsFragment.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call:Call<WeatherDTO>, t: Throwable?) {
            detailsLiveData.postValue(AppStateDetailsFragment.Error(Throwable(t?.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: WeatherDTO): AppStateDetailsFragment {
            val fact = serverResponse.fact
            return if (fact == null || fact.temp == null || fact.feels_like == null ||
                fact.condition.isNullOrEmpty()
            ) {
                AppStateDetailsFragment.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppStateDetailsFragment.Success(convertDtoToModel(serverResponse))
            }
        }

    }
}