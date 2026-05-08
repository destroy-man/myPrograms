package ru.korobeynikov.p0341preferencesdatastore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    text: String,
    onChangeText: (String) -> Unit,
    onSaveToSharedPreferences: suspend () -> Unit,
    onLoadFromSharedPreferences: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column {
        OutlinedTextField(text, onValueChange = { newValue ->
            onChangeText(newValue)
        })
        Row {
            Button(onClick = {
                scope.launch {
                    onSaveToSharedPreferences()
                }
            }) {
                Text("Save")
            }
            Button(onClick = {
                scope.launch {
                    onLoadFromSharedPreferences()
                }
            }) {
                Text("Load")
            }
        }
    }
}