package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_5.Email
import ru.korobeynikov.chapter7.chapter7_5.Person7_5_2

//Повторное использование логики обращения к свойству: делегирование свойств
//Использование делегирования свойств : отложенная инициализация и "bу lazy()"
@Composable
fun Chapter7_5_2() {
    val p = Person7_5_2("Alice")
    Text(text = " ${p.emails[0].address}")
}

fun loadEmails(person: Person7_5_2) = listOf(Email("${person.name}@mail.ru"))