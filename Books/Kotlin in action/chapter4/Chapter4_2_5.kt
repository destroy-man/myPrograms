package ru.korobeynikov.chapter4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_2.LengthCounter

//Объявление классов с нетривиальными конструкторами или свойствами
//Изменение видимости методов доступа
@Composable
fun Chapter4_2_5() {
    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    Text(text = " ${lengthCounter.counter}")
}