package ru.korobeynikov.chapter6

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_1.Person6_1_5

//Поддержка значения null
//Безопасное приведение типов: оператор "as?"
@Composable
fun Chapter6_1_5() {
    val p1 = Person6_1_5("Dmitry", "Jemerov")
    val p2 = Person6_1_5("Dmitry", "Jemerov")
    Column {
        Text(text = " ${p1 == p2}")
        Text(text = " ${p1.equals(42)}")
    }
}