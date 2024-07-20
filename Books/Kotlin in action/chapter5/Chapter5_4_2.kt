package ru.korobeynikov.chapter5

import android.util.Log
import androidx.compose.runtime.Composable

//Использование функциональных интерфейсов Java
//SАМ-конструкторы : явное преобразование лямбда-выражений в функциональные интерфейсы
@Composable
fun Chapter5_4_2() {
    createAllDoneRunnable().run()
}

fun createAllDoneRunnable() = Runnable { Log.d("myLogs", "All done!") }