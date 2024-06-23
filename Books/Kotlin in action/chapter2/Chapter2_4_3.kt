package ru.korobeynikov.chapter2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.util.TreeMap

//Итерации : циклы "whiLe" и "for"
//Итерации по элементам словарей
@Composable
fun Chapter2_4_3() {
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'F') {
        val binary = c.code.toString(2)
        binaryReps[c] = binary
    }
    Column {
        for ((letter, binary) in binaryReps)
            Text(text = " $letter = $binary")
    }
}