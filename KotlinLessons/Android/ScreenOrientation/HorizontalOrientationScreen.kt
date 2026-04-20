package ru.korobeynikov.screenorientation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalOrientationScreen() {
    Column {
        Text("Горизонтальная ориентация экрана")
        Row {
            Button(onClick = {}) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.height(100.dp)) {
                    Text("Button1")
                }
            }
            Button(onClick = {}) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.height(100.dp)) {
                    Text("Button2")
                }
            }
            Button(onClick = {}) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.height(100.dp)) {
                    Text("Button3")
                }
            }
            Button(onClick = {}) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.height(100.dp)) {
                    Text("Button4")
                }
            }
        }
    }
}