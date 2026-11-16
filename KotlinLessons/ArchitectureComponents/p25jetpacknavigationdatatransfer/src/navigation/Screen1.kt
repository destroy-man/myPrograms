package ru.korobeynikov.p25jetpacknavigationdatatransfer.navigation

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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Screen1(backStack: SnapshotStateList<Screen>) {
    var userText by remember { mutableStateOf("") }
    Column {
        Text("Screen 1", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = userText,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp),
            onValueChange = { newText ->
                userText = newText
            }
        )
        Row(modifier = Modifier.padding(top = 20.dp)) {
            Button(modifier = Modifier.padding(horizontal = 10.dp), onClick = {
                backStack.add(Screen.Screen2(userText))
            }) {
                Text("Next")
            }
        }
    }
}