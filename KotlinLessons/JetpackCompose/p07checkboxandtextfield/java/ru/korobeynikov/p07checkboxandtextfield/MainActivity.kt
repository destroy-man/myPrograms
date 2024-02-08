package ru.korobeynikov.p07checkboxandtextfield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = mutableStateOf("some text")
        setContent {
            HomeScreen(text = text, onValueChange = { newText ->
                text.value = newText
            })
        }
    }
}