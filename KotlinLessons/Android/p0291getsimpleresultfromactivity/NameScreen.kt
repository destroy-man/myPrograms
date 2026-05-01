package ru.korobeynikov.p0291getsimpleresultfromactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NameScreen(onReturnToPrevActivity: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text("Name")
            OutlinedTextField(
                name,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                onValueChange = { newValue ->
                    name = newValue
                }
            )
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            onReturnToPrevActivity(name)
        }) {
            Text("OK")
        }
    }
}