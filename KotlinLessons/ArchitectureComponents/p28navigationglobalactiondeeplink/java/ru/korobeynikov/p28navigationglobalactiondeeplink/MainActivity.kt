package ru.korobeynikov.p28navigationglobalactiondeeplink

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
        navController.navigate(R.id.navigation2)
    }

    fun onFragment1BackClick() {
        finish()
    }

    fun onFragment2NextClick() {
        navController.navigate(R.id.action_global_fragment3)
    }

    fun onFragment2BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment1)
    }

    fun onFragment3NextClick() {
        navController.navigate(R.id.fragment4)
    }

    fun onFragment3BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment2)
    }

    fun onFragment4NextClick() {}

    fun onFragment4BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.action_global_fragment3)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.fragment1 -> finish()
            R.id.fragment2 -> navController.navigate(R.id.fragment1)
            R.id.fragment3 -> navController.navigate(R.id.fragment2)
            R.id.fragment4 -> {
                navController.navigate(R.id.navigation2)
                navController.navigate(R.id.action_global_fragment3)
            }
        }
    }
}