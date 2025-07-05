package ru.korobeynikov.p07checkboxandtextfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(text: State<String>, onValueChange: (String) -> Unit) {
    val textValue = text.value
    OutlinedTextField(value = textValue, onValueChange = onValueChange)
}

@Composable
fun HomeScreenCheckbox(checked: State<Boolean>, onCheckedChange: (Boolean) -> Unit) {
    val checkedValue = checked.value
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        onCheckedChange.invoke(!checkedValue)
    }) {
        Checkbox(
            checked = checkedValue,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .padding(2.dp),
            onCheckedChange = null
        )
        Text(text = "Some checkbox text", fontSize = 18.sp)
    }
}