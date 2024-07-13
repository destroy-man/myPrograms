package ru.korobeynikov.chapter4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_3.CountingSet

//Методы, сгенерированные компилятором : классы данных и делегирование
//Делеrирование в классах. Ключевое слово by
@Composable
fun Chapter4_3_3() {
    val cset = CountingSet<Int>()
    cset.addAll(listOf(1, 1, 2))
    Text(text = " ${cset.objectsAdded} objects were added, ${cset.size} remain")
}