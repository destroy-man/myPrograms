package ru.korobeynikov.p27navigationui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_fragments, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, navController)
        return super.onOptionsItemSelected(item)
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

    fun onFragment3NextClick() {}

    fun onFragment3BackClick() {
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