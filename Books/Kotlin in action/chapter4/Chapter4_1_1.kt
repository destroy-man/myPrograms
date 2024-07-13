package ru.korobeynikov.chapter4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_1.Button

//Создание иерархий классов
//Интерфейсы в KotLin
@Composable
fun Chapter4_1_1() {
    val button = Button()
    Column {
        Text(text = " ${button.showOff()}")
        Text(text = " ${button.setFocus(true)}")
        Text(text = " ${button.click()}")
    }
}