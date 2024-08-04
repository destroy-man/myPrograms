package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_1.Point
import ru.korobeynikov.chapter7.chapter7_3.MutablePoint

//Соглашения для коллекций и диапазонов
//Обращение к элементам по индексам: "get" и "set"
@Composable
fun Chapter7_3_1() {
    val p = MutablePoint(10, 20)
    p[1] = 42
    Text(text = " $p")
}

operator fun Point.get(index: Int) = when (index) {
    0 -> x
    1 -> y
    else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
}

operator fun MutablePoint.set(index: Int, value: Int) {
    when (index) {
        0 -> x = value
        1 -> y = value
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}