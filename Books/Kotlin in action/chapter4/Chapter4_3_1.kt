package ru.korobeynikov.chapter4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_3.Client4_3_1

//Методы, сгенерированные компилятором : классы данных и делегирование
//Универсальные методы объектов
@Composable
fun Chapter4_3_1() {
    val client1 = Client4_3_1("Alice", 342562)
    val client2 = Client4_3_1("Alice", 342562)
    val processed = hashSetOf(Client4_3_1("Alice", 342562))
    Column {
        Text(text = " $client1")
        Text(text = " ${client1 == client2}")
        Text(text = " ${processed.contains(Client4_3_1("Alice", 342562))}")
    }
}