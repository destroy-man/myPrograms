package ru.korobeynikov.p0301getresultfromactivity.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(color: Color, alignment: Alignment.Horizontal, onOpenActivity: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Hello World",
            fontSize = 20.sp,
            color = color,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(alignment)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Button(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f),
                onClick = {
                    onOpenActivity(1)
                }
            ) {
                Text("Color")
            }
            Button(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f),
                onClick = {
                    onOpenActivity(2)
                }
            ) {
                Text("Alignment")
            }
        }
    }
}