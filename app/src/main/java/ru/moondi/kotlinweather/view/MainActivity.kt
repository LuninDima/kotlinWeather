package ru.moondi.kotlinweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.moondi.kotlinweather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_conteiner, MainFragment.newInstance()).commit()
        }
    }
}