package ru.moondi.kotlinweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<Any>{
            Toast.makeText(context,"Тест", Toast.LENGTH_LONG).show()}
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }
}