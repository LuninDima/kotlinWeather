package ru.moondi.kotlinweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.databinding.MainFragmentBinding
import ru.moondi.kotlinweather.viewmodel.AppState
import ru.moondi.kotlinweather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    var _binding: MainFragmentBinding? = null
    val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        val observer = Observer<Any> {
//            Toast.makeText(context, "Тест", Toast.LENGTH_LONG).show()
//        }
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeather()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> TODO("ошибка, невозможно получить данные")
            is AppState.Succes -> {
                val weatherData = appState.dataWeather
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG)
                setData(weatherData)
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

        }
    }

    private fun setData(weatherData: Weather) {
        binding.cityName.text = weatherData.city.name
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.long.toString()
        )
        binding.temperatureValue.text = weatherData.temperature.toString()
        binding.feelsLikeValue.text = weatherData.feelsLike.toString()
    }
}