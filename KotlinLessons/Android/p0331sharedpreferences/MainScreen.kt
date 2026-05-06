package ru.korobeynikov.p0331sharedpreferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(
    text: String,
    onChangeText: (String) -> Unit,
    onSaveToSharedPreferences: () -> Unit,
    onLoadFromSharedPreferences: () -> Unit
) {
    Column {
        OutlinedTextField(text, onValueChange = { newValue ->
            onChangeText(newValue)
        })
        Row {
            Button(onClick = onSaveToSharedPreferences) {
                Text("Save")
            }
            Button(onClick = onLoadFromSharedPreferences) {
                Text("Load")
            }
        }
    }
}