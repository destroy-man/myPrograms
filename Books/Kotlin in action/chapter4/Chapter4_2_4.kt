package ru.korobeynikov.chapter4

import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_2.User4_2_4

//Объявление классов с нетривиальными конструкторами или свойствами
//Обращение к полю из методов доступа
@Composable
fun Chapter4_2_4() {
    val user = User4_2_4("Alice")
    user.address = "Elsenheimerstrasse 47, 80687 Muenchen"
}