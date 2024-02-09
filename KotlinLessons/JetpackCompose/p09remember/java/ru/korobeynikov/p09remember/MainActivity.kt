package ru.korobeynikov.p09remember

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val counter = mutableIntStateOf(0)
        val checked = mutableStateOf(false)
        setContent {
            HomeScreen(counter = counter, onCounterClick = {
                counter.intValue++
            }, checked = checked, onCheckedChange = { checkedValue ->
                checked.value = checkedValue
            })
        }
    }
}