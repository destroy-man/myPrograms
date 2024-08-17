package ru.korobeynikov.chapter10

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

var counter = 0

//Рефлексия : интроспекция объектов Kotlin во время выполнения
//Механизм рефлексии в Kotlin : KClass, KCallable, KFunction и KProperty
@Composable
fun Chapter10_2_1() {
    val person = Person("Alice", 29)
    val kClass = person.javaClass.kotlin
    Column {
        Text(text = " ${kClass.simpleName}")
        kClass.members.forEach {
            Text(text = " ${it.name}")
        }
    }
}

fun foo(x: Int) = " $x"

fun sum(x: Int, y: Int) = x + y