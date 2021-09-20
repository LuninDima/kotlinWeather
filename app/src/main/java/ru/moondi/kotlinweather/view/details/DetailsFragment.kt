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
import ru.moondi.kotlinweather.databinding.FragmentDetailsBinding
import ru.moondi.kotlinweather.viewmodel.AppState
import ru.moondi.kotlinweather.viewmodel.MainViewModel

class DetailsFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        if (weather != null) {
            val city = weather.city
            binding.cityName.text = city.name
            binding.cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.long.toString()
            )
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()

        }

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

/*   override fun onActivityCreated(savedInstanceState: Bundle?) {
       super.onActivityCreated(savedInstanceState)
       viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
       viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
       viewModel.getWeather()
   }

   private fun renderData(appState: AppState) {
       when (appState) {
           is AppState.Error -> {
               Toast.makeText(context, "ошибка, данные не найдены", Toast.LENGTH_LONG).show()
           }
           is AppState.Succes -> {


                val weatherData = appState.dataWeather
                 binding.loadingLayout.visibility = View.GONE
                 Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG)
                 setData(weatherData)
           }
          is AppState.Loading -> {
              Toast.makeText(context, "Загрузка данных", Toast.LENGTH_LONG).show()
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
   }*/
