package ru.korobeynikov.ch02observer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.korobeynikov.ch02observer.composeobserver.WeatherStationScreenComposeObserver

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherStationScreenComposeObserver()
        }
    }
}