package ru.korobeynikov.chapter4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_4.Person
import ru.korobeynikov.chapter4.chapter4_4.fromJson

//Ключевое слово object: совместное объявление класса и его экземпляра
//Объекты-компаньоны как обычные объекты
@Composable
fun Chapter4_4_3() {
    val person = Person.Loader.fromJson("{name: 'Dmitry'}")
    val person2 = Person.fromJson("{name: 'Brent'}")
    Column {
        Text(text = " ${person.name}")
        Text(text = " ${person2.name}")
    }
}