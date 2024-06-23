package ru.korobeynikov.chapter2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Классы и свойства
//Свойства
@Composable
fun Chapter2_2_1(){
    val person = Person("Bob", true)
    Column {
        Text(text = " ${person.name}")
        Text(text = " ${person.isMarried}")
    }
}