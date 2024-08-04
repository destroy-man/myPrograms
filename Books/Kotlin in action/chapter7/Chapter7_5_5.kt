package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_5.Person7_5_5

//Повторное использование логики обращения к свойству: делегирование свойств
//Сохранение значений свойств в словаре
@Composable
fun Chapter7_5_5() {
    val p = Person7_5_5()
    val data = mapOf("name" to "Dmitry", "company" to "JetBrains")
    for ((attrName, value) in data)
        p.setAttribute(attrName, value)
    Text(text = " ${p.name}")
}