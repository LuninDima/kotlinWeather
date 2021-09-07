package ru.moondi.kotlinweather.view

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
import ru.moondi.kotlinweather.viewmodel.AppState
import ru.moondi.kotlinweather.viewmodel.MainViewModel

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private  val binding get() = _binding!!
    private  lateinit var viewModel: MainViewModel
    private  val adapter = MainFragmentAdapter()
    private  var isDataSetRus: Boolean = true

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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if(isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else{viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private  fun renderData(appState: AppState){
        when(appState){
            is AppState.Succes -> {
                binding.mainFragmentLoadingLayout.visibility =View.GONE
                adapter.setWeather(appState.dataWeather)
            }
            is AppState.Loading ->{
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error ->{
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainFragmentFAB, getString(R.string.error), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.reload)) {
                    viewModel.getWeatherFromLocalSourceRus()}.show()
                }
            }
        }
    companion object{
        fun newInstance() = MainFragment()
    }
    }