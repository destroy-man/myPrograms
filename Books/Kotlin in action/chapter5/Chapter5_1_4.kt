package ru.korobeynikov.chapter5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Лямбда-выражения и ссылки на члены класса
//Доступ к переменным из контекста
@Composable
fun Chapter5_1_4() {
    val responses = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
    Text(text = " ${printProblemCounts(responses)}")
}

fun printProblemCounts(responses: Collection<String>): String {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4"))
            clientErrors++
        else if (it.startsWith("5"))
            serverErrors++
    }
    return "$clientErrors client errors, $serverErrors server errors"
}