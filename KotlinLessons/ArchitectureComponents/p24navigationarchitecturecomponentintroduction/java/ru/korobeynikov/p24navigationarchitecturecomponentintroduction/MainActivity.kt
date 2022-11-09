package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun onFragment1NextClick() {
        navController.navigate(R.id.fragment2)
    }

    fun onFragment1BackClick() {
        finish()
    }

    fun onFragment2NextClick() {
        navController.navigate(R.id.fragment3)
    }

    fun onFragment2BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment1)
    }

    fun onFragment3NextClick() {
        navController.navigate(R.id.secondActivity)
    }

    fun onFragment3BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment2)
    }
}