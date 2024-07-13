package ru.korobeynikov.chapter4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_2.PrivateUser
import ru.korobeynikov.chapter4.chapter4_2.SubscribingUser

//Объявление классов с нетривиальными конструкторами или свойствами
//Реапизация свойств, объявпенных в интерфейсах
@Composable
fun Chapter4_2_3() {
    Column {
        Text(text = " ${PrivateUser("test@kotlinlang.org").nickname}")
        Text(text = " ${SubscribingUser("test@kotlinlang.org").nickname}")
    }
}