package ru.korobeynikov.p27jetpacknavigationbottomnavigationmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import ru.korobeynikov.p27jetpacknavigationbottomnavigationmenu.navigation.NavHostScreen

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