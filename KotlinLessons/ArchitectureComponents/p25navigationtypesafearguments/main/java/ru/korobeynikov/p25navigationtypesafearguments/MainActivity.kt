package ru.korobeynikov.p25navigationtypesafearguments

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
        val action = Fragment1Directions.actionToFragment2()
        action.setArg3("value3").setArg4(R.dimen.some_other_size).arg5 = R.string.hello_blank_fragment
        navController.navigate(action)
    }

    fun onFragment1BackClick() {
        finish()
    }

    fun onFragment2NextClick() {}

    fun onFragment2BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment1)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.fragment1 -> finish()
            R.id.fragment2 -> navController.navigate(R.id.fragment1)
        }
    }
}