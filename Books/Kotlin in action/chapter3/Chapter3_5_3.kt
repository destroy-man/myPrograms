package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Работа со строками и регулярными выражениями
//Мноrострочные литералы в тройных кавычках
@Composable
fun Chapter3_5_3() {
    val kotlinLogo = """| //
                     .|//
                     .|/ \"""
    Text(text = " ${kotlinLogo.trimMargin(".")}")
}