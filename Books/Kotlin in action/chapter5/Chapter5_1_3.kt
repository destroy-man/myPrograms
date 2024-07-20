package ru.korobeynikov.chapter5

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Лямбда-выражения и ссылки на члены класса
//Синтаксис лямбда-вы ражений
@Composable
fun Chapter5_1_3() {
    val sum = { x: Int, y: Int ->
        Log.d("myLogs", "Computing the sum of $x and $y...")
        x + y
    }
    Text(text = " ${sum(1, 2)}")
}