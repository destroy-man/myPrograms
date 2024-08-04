package ru.korobeynikov.chapter7

import android.util.Log
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_5.Person7_5_3

//Повторное использование логики обращения к свойству: делегирование свойств
//Реализация делегирования свойств
@Composable
fun Chapter7_5_3() {
    val p = Person7_5_3("Dmitry", 34, 2000)
    p.addPropertyChangeListener { event ->
        Log.d(
            "myLogs",
            "Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}"
        )
    }
    p.age = 35
    p.salary = 2100
}