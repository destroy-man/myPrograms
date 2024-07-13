package ru.korobeynikov.chapter4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_2.User4_2_1

//Объявление классов с нетривиальными конструкторами или свойствами
//Инициализация кпассов: основной конструктор и блоки инициализации
@Composable
fun Chapter4_2_1() {
    val alice = User4_2_1("Alice")
    val bob = User4_2_1("Bob", false)
    val carol = User4_2_1("Carol", isSubscribed = false)
    Column {
        Text(text = " ${alice.isSubscribed}")
        Text(text = " ${bob.isSubscribed}")
        Text(text = " ${carol.isSubscribed}")
    }
}