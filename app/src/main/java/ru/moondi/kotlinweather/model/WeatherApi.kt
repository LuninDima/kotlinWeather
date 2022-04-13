package ru.moondi.kotlinweather.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET("v2/informers")
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}