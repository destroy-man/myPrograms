package ru.korobeynikov.chapter7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_1.Point
import java.math.BigDecimal

//Перегрузка арифметических операторов
//Переrрузка унарных операторов
@Composable
fun Chapter7_1_3() {
    var bd = BigDecimal.ZERO
    Column {
        Text(text = " ${bd++}")
        Text(text = " ${++bd}")
    }
}

operator fun Point.unaryMinus() = Point(-x, -y)

operator fun BigDecimal.inc() = this + BigDecimal.ONE