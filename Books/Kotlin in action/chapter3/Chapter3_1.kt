package ru.korobeynikov.chapter3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Создание коллекций в KotLin
@Composable
fun Chapter3_1(){
    val set= hashSetOf(1,7,53)
    val list= arrayListOf(1,7,53)
    val map= hashMapOf(1 to "one",7 to "seven",53 to "fifty-three")
    Column {
        Text(text = " ${set.javaClass}")
        Text(text = " ${list.javaClass}")
        Text(text = " ${map.javaClass}")
    }
}