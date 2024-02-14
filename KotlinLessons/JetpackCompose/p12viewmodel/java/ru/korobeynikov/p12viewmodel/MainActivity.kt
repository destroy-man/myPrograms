package ru.korobeynikov.p12viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.korobeynikov.p12viewmodel.home_screen.HomeScreen
import ru.korobeynikov.p12viewmodel.home_screen.HomeViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.injectMainActivity(this)
        setContent {
            HomeScreen(homeViewModel = viewModel(factory = homeViewModelFactory))
        }
    }
}