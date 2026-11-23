package ru.korobeynikov.p28jetpacknavigationviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p28jetpacknavigationviewmodel.navigation.NavHostScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                NavHostScreen()
            }
        }
    }
}