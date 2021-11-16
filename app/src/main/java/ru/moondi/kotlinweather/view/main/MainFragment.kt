package ru.moondi.kotlinweather.view.main

import android.content.Context
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
import ru.moondi.kotlinweather.viewmodel.AppStateMainFragment
import ru.moondi.kotlinweather.viewmodel.MainViewModel

private const val IS_WORLD_KEY = "LIST_OF_TOWNS_KEY"
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var isDataSetWorld: Boolean = false
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
        // viewModel.getWeatherFromLocalSourceRus()
        showListOfTowns()
    }

    private fun showListOfTowns() {
        activity?.let {
            if (it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_WORLD_KEY, false)) {
                changeWeatherDataSet()
            } else {
                viewModel.getWeatherFromLocalSourceRus()
            }
        }
    }

    private fun changeWeatherDataSet() {
    if (isDataSetWorld)
    {
        viewModel.getWeatherFromLocalSourceRus()
        binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
    } else
    {
        viewModel.getWeatherFromLocalSourceWorld()
        binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
    }
    isDataSetWorld = !isDataSetWorld
    saveListOfTowns(isDataSetWorld)
}

    private fun saveListOfTowns(isDataSetWorld: Boolean) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()){
                putBoolean(IS_WORLD_KEY, isDataSetWorld)
                apply()
            }
        }
    }
    private fun renderData(appState: AppStateMainFragment) {
        with(binding) {
            with(includedLoadingLayout) {
                when (appState) {
                    is AppStateMainFragment.Success -> {
                        loadingLayout.visibility = View.GONE
                        adapter.setWeather(appState.dataWeather)
                        loadingLayout.showSnackBarNoAction(R.string.success)
                    }
                    is AppStateMainFragment.Loading -> {
                        loadingLayout.visibility = View.VISIBLE
                        loadingLayout.showSnackBarNoAction(R.string.load)
                    }
                    is AppStateMainFragment.Error -> {
                        loadingLayout.visibility = View.GONE
                        loadingLayout.showSnackBar(
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
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    private fun View.showSnackBarNoAction(
        resourceID: Int,
        length: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(this, requireActivity().resources.getString(resourceID), length).show()
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

