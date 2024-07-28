package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_1.Person6_1_11

//Поддержка значения null
//Допустимость значения null и Java
@Composable
fun Chapter6_1_11() {
    Text(text = yellAtSafe(Person6_1_11(null)))
}

fun yellAt(person: Person6_1_11) = " ${person.name.uppercase()}!!!"

fun yellAtSafe(person: Person6_1_11) = " ${(person.name ?: "Anyone").uppercase()}!!!"