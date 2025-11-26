package ru.korobeynikov.ch03decorator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.korobeynikov.ch03decorator.kotlindecorator.StarbuzzCoffeeScreenKotlinDecorator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarbuzzCoffeeScreenKotlinDecorator()
        }
    }
}