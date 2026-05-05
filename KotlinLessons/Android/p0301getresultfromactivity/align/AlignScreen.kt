package ru.korobeynikov.p0301getresultfromactivity.align

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlignScreen(onAlignmentSelect: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            onClick = {
                onAlignmentSelect("start")
            }
        ) {
            Text("Start")
        }
        Button(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            onClick = {
                onAlignmentSelect("center")
            }
        ) {
            Text("Center")
        }
        Button(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            onClick = {
                onAlignmentSelect("end")
            }
        ) {
            Text("End")
        }
    }
}