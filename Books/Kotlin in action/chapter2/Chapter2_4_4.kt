package ru.korobeynikov.chapter2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Итерации : циклы "whiLe" и "for"
//Использование "in" дпя проверки вхождения в диапазон ипи коллекцию
@Composable
fun Chapter2_4_4(){
    Column {
        Text(text = " ${recognize('8')}")
    }
}

fun isLetter(c:Char)=c in 'a'..'z' || c in 'A'..'Z'

fun isNotDigit(c: Char)=c !in '0'..'9'

fun recognize(c:Char)=when(c){
    in '0'..'9'->"It's a digit!"
    in 'a'..'z',in 'A'..'Z'->"It's a letter!"
    else->"I don't know.."
}