package ru.korobeynikov.p0301getresultfromactivity.color

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColorScreen(onColorSelect: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            onClick = {
                onColorSelect("red")
            }
        ) {
            Text("Red")
        }
        Button(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            onClick = {
                onColorSelect("green")
            }
        ) {
            Text("Green")
        }
        Button(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            onClick = {
                onColorSelect("blue")
            }
        ) {
            Text("Blue")
        }
    }
}