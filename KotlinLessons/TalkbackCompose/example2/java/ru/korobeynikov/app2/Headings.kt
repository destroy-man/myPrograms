package ru.korobeynikov.app2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

@Composable
fun Headings() {
    Text(
        modifier = Modifier.semantics { heading() },
        text = "Заголовок 1"
    )
}