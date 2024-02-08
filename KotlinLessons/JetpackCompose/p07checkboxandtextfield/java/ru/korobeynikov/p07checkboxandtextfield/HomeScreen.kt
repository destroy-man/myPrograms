package ru.korobeynikov.p07checkboxandtextfield

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
fun HomeScreen(text: State<String>, onValueChange: (String) -> Unit) =
    OutlinedTextField(value = text.value, onValueChange = onValueChange)