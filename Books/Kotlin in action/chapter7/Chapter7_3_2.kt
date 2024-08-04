package ru.korobeynikov.chapter7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_1.Point
import ru.korobeynikov.chapter7.chapter7_3.Rectangle

//Соглашения для коллекций и диапазонов
//Соглашение "in"
@Composable
fun Chapter7_3_2() {
    val rect = Rectangle(Point(10, 20), Point(50, 50))
    Column {
        Text(text = " ${Point(20, 30) in rect}")
        Text(text = " ${Point(5, 5) in rect}")
    }
}

operator fun Rectangle.contains(p: Point) =
    p.x in upperLeft.x until lowerRight.x && p.y in upperLeft.y until lowerRight.y