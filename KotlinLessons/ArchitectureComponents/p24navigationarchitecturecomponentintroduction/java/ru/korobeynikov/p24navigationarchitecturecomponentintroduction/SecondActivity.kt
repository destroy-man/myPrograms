package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

class SecondActivity : AppCompatActivity(), Fragment4Callback, Fragment5Callback {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onFragment4NextClick() {
        navController.navigate(R.id.fragment5)
    }

    override fun onFragment4BackClick() {
        finish()
    }

    override fun onFragment5NextClick() {}

    override fun onFragment5BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment4)
    }
}