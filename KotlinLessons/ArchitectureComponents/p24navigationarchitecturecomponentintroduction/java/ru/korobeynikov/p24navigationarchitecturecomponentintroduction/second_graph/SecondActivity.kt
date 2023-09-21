package ru.korobeynikov.p24navigationarchitecturecomponentintroduction.second_graph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.R
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun fragment4BackClick() = finish()

    fun fragment4NextClick() = navController.navigate(R.id.fragment5)

    fun fragment5BackClick() = navController.popBackStack()

    fun fragment5NextClick() {}
}