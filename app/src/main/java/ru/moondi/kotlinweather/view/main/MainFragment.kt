package ru.moondi.kotlinweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.databinding.FragmentMainBinding
import ru.moondi.kotlinweather.view.DetailsFragment
import ru.moondi.kotlinweather.model.Weather
import ru.moondi.kotlinweather.viewmodel.AppState
import ru.moondi.kotlinweather.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var isDataSetRus: Boolean = true
    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.fragment_conteiner, DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() =
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }.also {isDataSetRus = !isDataSetRus }

    private fun renderData(appState: AppState) {
        with(binding) {
            with(mainFragmentLoadingLayout) {
                when (appState) {
                    is AppState.Succes -> {
                        mainFragmentLoadingLayout.visibility = View.GONE
                        adapter.setWeather(appState.dataWeather)
                        showSnackBarNoAction(R.string.success)
                    }
                    is AppState.Loading -> {
                        mainFragmentLoadingLayout.visibility = View.VISIBLE
                        showSnackBarNoAction(R.string.load)
                    }
                    is AppState.Error -> {
                        mainFragmentLoadingLayout.visibility = View.GONE
                        showSnackBar(
                            getString(R.string.error),
                            getString(R.string.reload),
                            {
                                viewModel.getWeatherFromLocalSourceRus()
                            })
                    }
                }
            }
        }


    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this,text, length).setAction(actionText, action).show()
    }

    private fun View.showSnackBarNoAction(
        resourceID: Int,
        length: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(this,requireActivity().resources.getString(resourceID), length).show()
    }


    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

