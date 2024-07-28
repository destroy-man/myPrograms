package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_1.Person6_1_3

//Поддержка значения null
//Оператор безопасного вызова: "?."
@Composable
fun Chapter6_1_3() {
    val person = Person6_1_3("Dmitry", null)
    Text(text = " ${person.countryName()}")
}

fun Person6_1_3.countryName(): String {
    val country = this.company?.address?.country
    return if (country != null) country else "Unknown"
}