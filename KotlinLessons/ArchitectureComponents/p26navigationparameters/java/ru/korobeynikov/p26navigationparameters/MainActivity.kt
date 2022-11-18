package ru.korobeynikov.p26navigationparameters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun onFragment1NextClick() {
        val builder = NavOptions.Builder()
        val navOptions = builder.setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left).build()
        navController.navigate(R.id.fragment2, null, navOptions)
    }

    fun onFragment1BackClick() {
        finish()
    }

    fun onFragment2NextClick() {
        navController.navigate(R.id.action_fragment2_to_fragment3)
    }

    fun onFragment2BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment1)
    }

    fun onFragment3NextClick() {
        val bundle = Bundle()
        bundle.putString("folder", "camera")
        bundle.putString("id", "100")
        navController.navigate(R.id.secondActivity, bundle)
    }

    fun onFragment3BackClick() {
        if (navController.popBackStack())
            navController.navigate(R.id.fragment1)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.fragment1 -> finish()
            R.id.fragment2 -> navController.navigate(R.id.fragment1)
            R.id.fragment3 -> navController.navigate(R.id.fragment1)
        }
    }
}