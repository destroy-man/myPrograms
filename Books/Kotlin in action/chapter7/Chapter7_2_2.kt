package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_2.Person7_2_2

//Перегрузка операторов сравнения
//Операторы отношения : compareTo
@Composable
fun Chapter7_2_2() {
    val p1 = Person7_2_2("Alice", "Smith")
    val p2 = Person7_2_2("Bob", "Johnson")
    Text(text = " ${p1 < p2}")
}