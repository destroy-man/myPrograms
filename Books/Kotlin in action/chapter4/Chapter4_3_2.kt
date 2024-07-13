package ru.korobeynikov.chapter4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_3.Client4_3_2

//Методы, сгенерированные компилятором : классы данных и делегирование
//Кпассы данных: автоматическая rенерация универсальных методов
@Composable
fun Chapter4_3_2() {
    val bob = Client4_3_2("Bob", 973293)
    Text(text = " ${bob.copy(postalCode = 382555)}")
}