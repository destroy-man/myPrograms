package ru.korobeynikov.chapter9

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Вариантность: обобщенные типы и подтипы
//Проекция со звездочкой : использование * вместо типового арrумента
@Composable
fun Chapter9_3_6() {
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)
    Column {
        Text(text = " ${Validators[String::class].validate("Kotlin")}")
        Text(text = " ${Validators[Int::class].validate(42)}")
    }
}

fun <T> printFirst(list: List<T>) {
    if (list.isNotEmpty())
        Log.d("myLogs", " ${list.first()}")
}