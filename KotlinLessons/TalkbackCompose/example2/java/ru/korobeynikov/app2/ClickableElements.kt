package ru.korobeynikov.app2

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun ClickableElements() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClickLabel = "Что-то делает") {
                Toast
                    .makeText(context, "Нажатие на кликабельный элемент", Toast.LENGTH_SHORT)
                    .show()
            }
    ) {
        Text("OK")
    }
}

@Composable
fun ClickableSemantics() {
    val context = LocalContext.current
    Box(modifier = Modifier
        .padding(10.dp)
        .semantics {
            onClick(label = "Что-то делает") {
                Toast
                    .makeText(context, "Нажатие на кликабельный элемент", Toast.LENGTH_SHORT)
                    .show()
                true
            }
    }) {
        Text("OK")
    }
}