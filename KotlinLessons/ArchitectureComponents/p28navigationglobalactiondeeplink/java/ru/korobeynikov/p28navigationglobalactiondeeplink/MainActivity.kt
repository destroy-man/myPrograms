package ru.korobeynikov.p28navigationglobalactiondeeplink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.korobeynikov.p28navigationglobalactiondeeplink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun fragment1NextClick() = navController.navigate(R.id.navigation2)

    fun fragment1BackClick() {}

    fun fragment2NextClick() = navController.navigate(R.id.fragment3)

    fun fragment2BackStack() = navController.popBackStack()

    fun fragment3NextClick() = navController.navigate(R.id.fragment4)

    fun fragment3BackClick() = navController.popBackStack()

    fun fragment4NextClick() {}

    fun fragment4BackClick() = navController.popBackStack()
}