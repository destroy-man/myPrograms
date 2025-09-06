package ru.korobeynikov.p12viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.korobeynikov.p12viewmodel.dagger.App
import ru.korobeynikov.p12viewmodel.dagger.HomeScreenDagger
import ru.korobeynikov.p12viewmodel.dagger.HomeViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.injectMainActivity(this)
        setContent {
            HomeScreenDagger(homeViewModel = viewModel(factory = homeViewModelFactory))
        }
    }
}