package ru.korobeynikov.chapter4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_4.User4_4_2

//Ключевое слово object: совместное объявление класса и его экземпляра
//Объекты-компаньоны : место для фабричных методов и статических членов класса
@Composable
fun Chapter4_4_2() {
    val subscribingUser = User4_4_2.newSubscribingUser("bob@gmail.com")
    val facebookUser = User4_4_2.newFacebookUser(4)
    Text(text = " ${subscribingUser.nickname}")
}