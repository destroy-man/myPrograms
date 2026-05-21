package ru.korobeynikov.p0701saveactivitystate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen(onChangeCount: () -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    Column {
        Button(onClick = onChangeCount) {
            Text(stringResource(R.string.count))
        }
        OutlinedTextField(text, modifier = Modifier.fillMaxWidth(), onValueChange = { newValue ->
            text = newValue
        })
    }
}