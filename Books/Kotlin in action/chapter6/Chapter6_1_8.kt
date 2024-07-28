package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_1.MyService

//Поддержка значения null
//Свойства с отложенной инициализацией
class Chapter6_1_8 {

    private lateinit var myService: MyService

    @Composable
    fun MyServiceComposable() {
        myService = MyService()
        Text(text = myService.performAction())
    }
}