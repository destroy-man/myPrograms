package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity(), Fragment1Callback, Fragment2Callback, Fragment3Callback {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onFragment1NextClick() {
        navController.navigate(R.id.action_fragment1_to_fragment2)
    }

    override fun onFragment1BackClick() {
        finish()
    }

    override fun onFragment2NextClick() {
        navController.navigate(R.id.fragment3)
    }

    override fun onFragment2BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment1)
    }

    override fun onFragment3NextClick() {
        navController.navigate(R.id.secondActivity)
    }

    override fun onFragment3BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment2)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.fragment1 -> finish()
            R.id.fragment2 -> navController.navigate(R.id.fragment1)
            R.id.fragment3 -> navController.navigate(R.id.fragment2)
        }
    }
}