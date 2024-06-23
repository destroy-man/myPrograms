package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter2.Color.*

//Представление и обработка выбора : перечисления и конструкция "when"
//Выражение "when" без арrументов
@Composable
fun Chapter2_3_4(){
    Text(text = " ${mixOptimized(BLUE, YELLOW)}")
}

fun mixOptimized(c1: Color, c2: Color) = when {
    (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c2 == RED) -> ORANGE
    (c1 == YELLOW && c2 == BLUE) || (c1 == BLUE && c2 == YELLOW) -> GREEN
    (c1 == BLUE && c2 == VIOLET) || (c1 == VIOLET && c2 == BLUE) -> INDIGO
    else -> throw Exception("Dirty color")
}