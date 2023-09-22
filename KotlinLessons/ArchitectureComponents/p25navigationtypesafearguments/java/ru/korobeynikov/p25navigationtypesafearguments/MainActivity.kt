package ru.korobeynikov.p25navigationtypesafearguments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.korobeynikov.p25navigationtypesafearguments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun fragment1NextClick() {
        val bundle = Bundle()
        bundle.putString("arg1", "value1")
        bundle.putInt("arg2", 2)
        navController.navigate(R.id.fragment2, bundle)
    }

    fun fragment1BackClick() {}

    fun fragment2NextClick() {}

    fun fragment2BackStack() = navController.popBackStack()
}