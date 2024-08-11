package ru.korobeynikov.chapter9

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Обобщенные типы во время выполнения: стирание и овеществление параметров типов
//Объявление функций с овеществляемыми типовыми параметрами
@Composable
fun Chapter9_2_2() {
    Column {
        Text(text = " ${isA<String>("abc")}")
        Text(text = " ${isA<String>(123)}")
    }
}

inline fun <reified T> isA(value: Any) = value is T