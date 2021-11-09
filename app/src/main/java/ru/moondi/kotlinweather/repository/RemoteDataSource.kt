package ru.moondi.kotlinweather.repository

import android.os.Handler
import android.view.View
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.moondi.kotlinweather.BuildConfig
import ru.moondi.kotlinweather.model.WeatherApi
import ru.moondi.kotlinweather.model.WeatherDTO
import ru.moondi.kotlinweather.model.YANDEX_WEATHER_API_URL

private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class RemoteDataSource {
    private val weatherApi = Retrofit.Builder()
        .baseUrl(YANDEX_WEATHER_API_URL)
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        ).build().create(WeatherApi::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callBack: Callback<WeatherDTO>) {
        weatherApi.getWeather(BuildConfig.MY_YANDEX_API_KEY_VALUE, lat, lon).enqueue(callBack)
    }
}