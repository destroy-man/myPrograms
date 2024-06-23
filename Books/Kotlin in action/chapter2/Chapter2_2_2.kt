package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Классы и свойства
//Собственные методы доступа
@Composable
fun Chapter2_2_2(){
    Text(text = " ${createRandomRectangle().isSquare}")
}