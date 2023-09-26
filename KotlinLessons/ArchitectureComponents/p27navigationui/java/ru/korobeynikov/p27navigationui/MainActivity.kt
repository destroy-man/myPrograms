package ru.korobeynikov.p27navigationui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import ru.korobeynikov.p27navigationui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun fragment1NextClick() = navController.navigate(R.id.fragment2)

    fun fragment1BackClick() {}

    fun fragment2NextClick() = navController.navigate(R.id.fragment3)

    fun fragment2BackStack() = navController.popBackStack()

    fun fragment3NextClick() {}

    fun fragment3BackClick() = navController.popBackStack()
}