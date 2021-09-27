package ru.moondi.kotlinweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.databinding.ActivityMainBinding
import ru.moondi.kotlinweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState.let{
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_conteiner, MainFragment.newInstance()).commit()
        }
    }
}