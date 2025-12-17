package ru.korobeynikov.ch05singleton.singletonkotlin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChocolateBoilerScreen() {
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        val boiler1 = ChocolateBoiler
        Text("${boiler1.hashCode()}")
        val boiler2 = ChocolateBoiler
        Text("${boiler2.hashCode()}")
    }
}