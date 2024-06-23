package ru.korobeynikov.chapter2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter2.Color.*

//Представление и обработка выбора : перечисления и конструкция "when"
//Испопьзование оператора "when" с классами перечислений
@Composable
fun Chapter2_3_2(){
    Column {
        Text(text = " ${getMnemonic(BLUE)}")
        Text(text = " ${getWarmth(ORANGE)}")
    }
}

fun getMnemonic(color: Color) = when (color) {
    RED -> "Каждый"
    ORANGE -> "Охотник"
    YELLOW -> "Желает"
    GREEN -> "Знать"
    BLUE -> "Где"
    INDIGO -> "Сидит"
    VIOLET -> "Фазан"
}

fun getWarmth(color: Color) = when (color) {
    RED, ORANGE, YELLOW -> "теплый"
    GREEN -> "нейтральный"
    BLUE, INDIGO, VIOLET -> "холодный"
}