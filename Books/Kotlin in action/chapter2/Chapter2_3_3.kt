package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter2.Color.*

//Представление и обработка выбора : перечисления и конструкция "when"
//Использование оператора "when" с произвольными объектами
@Composable
fun Chapter2_3_3(){
    Text(text = " ${mix(BLUE, YELLOW)}")
}

fun mix(c1: Color, c2: Color) = when (setOf(c1, c2)) {
    setOf(RED, YELLOW) -> ORANGE
    setOf(YELLOW, BLUE) -> GREEN
    setOf(BLUE, VIOLET) -> INDIGO
    else -> throw Exception("Грязный цвет")
}