package ru.korobeynikov.p06state

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(counter: State<Int>, onCounterClick: () -> Unit) {
    val counterValue = counter.value
    Text(text = "Clicks: $counterValue", modifier = Modifier.clickable(onClick = onCounterClick))
}