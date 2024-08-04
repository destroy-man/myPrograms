package ru.korobeynikov.chapter7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_1.Point

//Перегрузка операторов сравнения
//Операторы равенства : "equals"
@Composable
fun Chapter7_2_1() {
    Column {
        Text(text = " ${Point(10, 20) == Point(10, 20)}")
        Text(text = " ${Point(10, 20) != Point(5, 5)}")
        Text(text = " ${null == Point(1, 2)}")
    }
}