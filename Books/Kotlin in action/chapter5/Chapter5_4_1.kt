package ru.korobeynikov.chapter5

import android.util.Log
import androidx.compose.runtime.Composable

//Использование функциональных интерфейсов Java
//Передача лямбда-выражения в Jаvа-метод
@Composable
fun Chapter5_4_1() {
    Thread {
        Log.d("myLogs", "42")
    }.start()
}