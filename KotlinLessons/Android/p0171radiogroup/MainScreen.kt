package ru.korobeynikov.p0171radiogroup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val buttonList = remember { mutableStateListOf<ButtonData>() }
    val radioOptions = listOf("Start", "Center", "End")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }
    var buttonName by remember { mutableStateOf("") }
    Column {
        HorizontalRadioGroup(radioOptions, selectedOption) { text ->
            selectedOption = text
        }
        Row {
            OutlinedTextField(
                buttonName,
                modifier = Modifier.weight(1f),
                onValueChange = { newValue ->
                    buttonName = newValue
                }
            )
            Button(onClick = {
                buttonList.add(
                    ButtonData(
                        name = buttonName,
                        alignment = when (selectedOption) {
                            "Start" -> Alignment.Start
                            "Center" -> Alignment.CenterHorizontally
                            else -> Alignment.End
                        }
                    )
                )
            }) {
                Text("Create")
            }
            Button(onClick = {
                buttonList.clear()
                Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show()
            }) {
                Text("Clear")
            }
        }
        buttonList.forEach { button ->
            Button(modifier = Modifier.align(button.alignment), onClick = {}) {
                Text(button.name)
            }
        }
    }
}

@Composable
fun HorizontalRadioGroup(
    radioOptions: List<String>,
    selectedOption: String,
    onChangeRadioOption: (String) -> Unit
) {
    Row {
        radioOptions.forEach { text ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                onChangeRadioOption(text)
            }) {
                RadioButton(selected = text == selectedOption, onClick = {
                    onChangeRadioOption(text)
                })
                Text(text = text)
            }
        }
    }
}

@Composable
fun VerticalRadioGroup(
    radioOptions: List<String>,
    selectedOption: String,
    onChangeRadioOption: (String) -> Unit
) {
    Column {
        radioOptions.forEach { text ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                onChangeRadioOption(text)
            }) {
                RadioButton(selected = text == selectedOption, onClick = {
                    onChangeRadioOption(text)
                })
                Text(text = text)
            }
        }
    }
}

data class ButtonData(
    val name: String,
    val alignment: Alignment.Horizontal
)