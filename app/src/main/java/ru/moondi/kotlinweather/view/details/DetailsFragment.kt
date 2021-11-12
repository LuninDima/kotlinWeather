package ru.moondi.kotlinweather.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import coil.api.load
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import okhttp3.*
import ru.moondi.kotlinweather.BuildConfig
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.databinding.FragmentDetailsBinding
import ru.moondi.kotlinweather.model.*
import ru.moondi.kotlinweather.utils.CircleTransformation
import ru.moondi.kotlinweather.utils.showSnackBar
import ru.moondi.kotlinweather.utils.translation
import ru.moondi.kotlinweather.view.details.DetailsService
import ru.moondi.kotlinweather.viewmodel.AppState
import ru.moondi.kotlinweather.viewmodel.DetailsViewModel
import java.io.IOException

private const val TEMP_INVALID = -100
private const val FEELS_LIKE_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"

private val translations = mapOf(
    "clear" to "Ясно",
    "partly-cloudy" to "Малооблачно",
    "cloudy" to "Облачно с прояснениями",
    "overcast" to "Пасмурно",
    "drizzle" to "Морось",
    "light-rain" to "Небольшой дождь",
    "rain" to "Дождь",
    "moderate-rain" to "Умеренно сильный дождь",
    "heavy-rain" to "Сильный дождь",
    "continuous-heavy-rain" to "Длительный сильный дождь",
    "showers" to "Ливень",
    "wet-snow" to "Дождь со снегом",
    "light-snow" to "Небольшой снег",
    "snow" to "Снег",
    "snow-showers" to "Снегопад",
    "hail" to "Град",
    "thunderstorm" to "Гроза",
    "thunderstorm-with-rain" to "Дождь с грозой",
    "thunderstorm-with-hail" to "Гроза с градом",
    "night" to "Ночь",
    "morning" to "Утро",
    "day" to "День",
    "evening" to "вечер"
)

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getWeatherFromRemoteSource(
            weatherBundle.city.lat,
            weatherBundle.city.long
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Succes -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeater(appState.dataWeather[0])
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromRemoteSource(
                            weatherBundle.city.lat,
                            weatherBundle.city.long
                        )
                    }
                )
            }
        }
    }

    private fun setWeater(weather: Weather) {
        val city = weatherBundle.city
        binding.cityName.text = city.name
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            city.lat.toString(),
            city.long.toString()
        )
        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
        var condition = translation(weather.condition)
        binding.weatherCondition.text = condition



        Picasso.get()
            .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
            .transform(CircleTransformation())
            .into(headerIcon)

            //headerIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")

        weather.icon?.let {
            GlideToVectorYou.justLoadImage(
                activity,
                Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
                weatherIcon
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
