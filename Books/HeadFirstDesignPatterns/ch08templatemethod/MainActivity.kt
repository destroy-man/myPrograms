package ru.korobeynikov.ch08templatemethod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.korobeynikov.ch08templatemethod.sortduck.DuckSortScreen
import ru.korobeynikov.ch08templatemethod.withhook.BeverageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                DuckSortScreen()
            }
        }

        /*
        var teaAnswer by mutableStateOf("")
        var coffeeAnswer by mutableStateOf("")
        setContent {
            Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                BeverageScreen(teaAnswer, coffeeAnswer, onChangeTeaAnswer = { answer ->
                    teaAnswer = answer
                }, onChangeCoffeeAnswer = { answer ->
                    coffeeAnswer = answer
                })
            }
        }
         */
    }
}