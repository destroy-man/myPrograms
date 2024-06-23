package ru.korobeynikov.chapter2

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Представление и обработка выбора : перечисления и конструкция "when"
//Блоки в выражениях "if" и "when"
@Composable
fun Chapter2_3_7() {
    Text(text = " ${evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4)))}")
}

fun evalWithLogging(e: Expr): Int = when (e) {
    is Num -> {
        Log.d("myLogs", "num: ${e.value}")
        e.value
    }

    is Sum -> {
        val left = evalWithLogging(e.left)
        val right = evalWithLogging(e.right)
        Log.d("myLogs", "sum: $left + $right")
        left + right
    }

    else -> throw IllegalArgumentException("Unknown expression")
}