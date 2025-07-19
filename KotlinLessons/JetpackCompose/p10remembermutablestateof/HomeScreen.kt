package ru.korobeynikov.p10remembermutablestateof

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    var checked by rememberSaveable { mutableStateOf(false) }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked, onCheckedChange = { value ->
                checked = value
            })
            Text(text = "More details", fontSize = 18.sp)
        }
        if (checked) {
            Text(text = stringResource(id = R.string.details))
        }
    }
}

@Composable
fun HomeScreenOuterState(checked: State<Boolean>, onCheckedChange: (Boolean) -> Unit) {
    val checkedValue = checked.value
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkedValue, onCheckedChange = onCheckedChange)
        Text(text = "Some important preference", fontSize = 18.sp)
    }
}