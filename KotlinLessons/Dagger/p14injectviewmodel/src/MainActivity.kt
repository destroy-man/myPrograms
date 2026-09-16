package ru.korobeynikov.p14injectviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.korobeynikov.p14injectviewmodel.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen(
                    orderViewModel = viewModel(factory = viewModelFactory),
                    userViewModel = viewModel(factory = viewModelFactory)
                )
            }
        }
    }
}