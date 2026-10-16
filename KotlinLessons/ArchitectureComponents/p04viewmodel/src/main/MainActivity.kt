package ru.korobeynikov.p04viewmodel.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import ru.korobeynikov.p04viewmodel.viewmodels.ViewModelWithContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelWithContext = ViewModelWithContext(application)
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen(viewModelWithContext = viewModelWithContext)
            }
        }
    }
}