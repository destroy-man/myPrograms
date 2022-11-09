package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

class SecondActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun onFragment4NextClick() {
        navController.navigate(R.id.fragment5)
    }

    fun onFragment4BackClick() {
        finish()
    }

    fun onFragment5NextClick() {}

    fun onFragment5BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment4)
    }
}