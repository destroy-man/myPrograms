package ru.korobeynikov.ch01introduction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.korobeynikov.ch01introduction.strategy.duck.MiniDuckSimulatorScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniDuckSimulatorScreen()
        }
    }
}