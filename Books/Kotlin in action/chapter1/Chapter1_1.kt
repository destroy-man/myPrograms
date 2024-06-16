package ru.korobeynikov.chapter1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Знакомство с Kotlin
@Composable
fun Chapter1_1(){
    val persons = listOf(Person("Alice"), Person("Bob", age = 29))
    val oldest = persons.maxBy { it.age ?: 0 }
    Text(text = " The oldest is: $oldest")
}