package ru.korobeynikov.chapter2

import android.util.Log
import androidx.compose.runtime.Composable

//Итерации : циклы "whiLe" и "for"
//Итерации по поспедоватепьности чисел : диапазоны и проrрессии
@Composable
fun Chapter2_4_2() {
    for (i in 100 downTo 1 step 2)
        Log.d("myLogs", " ${fizzBuzz(i)}")
}

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}