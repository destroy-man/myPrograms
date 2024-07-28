package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Поддержка значения null
//Функция let
@Composable
fun Chapter6_1_7() {
    var email: String? = "yole@example.com"
    email?.let {
        Text(text = sendEmailTo(it))
    }
    email = null
    email?.let {
        Text(text = sendEmailTo(it))
    }
}

fun sendEmailTo(email: String) = " Sending email to $email"