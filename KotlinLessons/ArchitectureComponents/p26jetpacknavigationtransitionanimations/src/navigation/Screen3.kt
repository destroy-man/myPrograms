package ru.korobeynikov.p26jetpacknavigationtransitionanimations.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Screen3(backStack: SnapshotStateList<Screen>) {
    Column {
        Text("Screen 3", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.padding(top = 20.dp)) {
            Button(modifier = Modifier.padding(horizontal = 10.dp), onClick = {
                backStack.removeLastOrNull()
            }) {
                Text("Back")
            }
        }
        BackHandler {
            backStack.removeLastOrNull()
        }
    }
}