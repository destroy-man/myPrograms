package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_1.Point

//Перегрузка арифметических операторов
//Переrрузка бинарных арифметических операций
@Composable
fun Chapter7_1_1() {
    val p = Point(10, 20)
    Text(text = " ${p * 1.5}")
}

operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)

operator fun Point.times(scale: Double) = Point((x * scale).toInt(), (y * scale).toInt())

operator fun Char.times(count: Int) = toString().repeat(count)