package ru.korobeynikov.p06state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableIntStateOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val counter = mutableIntStateOf(0)
        setContent {
            HomeScreen(counter = counter, onCounterClick = {
                counter.intValue++
            })
        }
    }
}