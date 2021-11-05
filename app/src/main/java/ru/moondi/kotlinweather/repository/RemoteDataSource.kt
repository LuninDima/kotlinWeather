package ru.moondi.kotlinweather.repository

import android.os.Handler
import android.view.View
import com.google.gson.Gson
import okhttp3.*
import ru.moondi.kotlinweather.BuildConfig

private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class RemoteDataSource {

    fun getWeatherDetails(requestLink: String, callBack: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
            header(REQUEST_API_KEY, BuildConfig.MY_YANDEX_API_KEY_VALUE)
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callBack)
    }
}