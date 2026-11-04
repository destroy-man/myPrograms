package ru.korobeynikov.p18savedstatehandle

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val data by mainViewModel.data.collectAsState()
    var editText by remember { mutableStateOf(data) }
    Column {
        OutlinedTextField(value = editText, onValueChange = { newText ->
            editText = newText
        })
        Button(onClick = {
            mainViewModel.updateData(editText)
        }) {
            Text("Изменить текст")
        }
        Text(data)
    }
}