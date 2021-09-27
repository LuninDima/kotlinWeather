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
        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
            with(weather) {
                val city = city
                with(binding) {
                    with(city) {
                        cityName.text = name
                        cityCoordinates.text = String.format(
                            getString(R.string.city_coordinates),
                            lat.toString(),
                            long.toString()
                        )
                    }
                    temperatureValue.text = temperature.toString()
                    feelsLikeValue.text = feelsLike.toString()
                }
            }
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
