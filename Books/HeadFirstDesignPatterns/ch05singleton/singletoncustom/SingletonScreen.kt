package ru.korobeynikov.ch05singleton.singletoncustom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SingletonScreen() {
    //Паттерн Синглтон кастомная реализация
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        val singleton1 = Singleton.getInstance()
        Text("${singleton1.hashCode()}")
        val singleton2 = Singleton.getInstance()
        Text("${singleton2.hashCode()}")
    }
}