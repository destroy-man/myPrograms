package ru.korobeynikov.p09remember

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val TAG = "myLogs"

@Composable
fun HomeScreen(
    counter: State<Int>,
    onCounterClick: () -> Unit,
    checked: State<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
) {
    val checkedValue = checked.value
    Column {
        ClickCounter(
            uppercase = checkedValue,
            counterValue = counter.value,
            onCounterClick = onCounterClick
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checkedValue, onCheckedChange = onCheckedChange)
            Text(text = "Uppercase")
        }
    }
}

@Composable
fun ClickCounter(uppercase: Boolean, counterValue: Int, onCounterClick: () -> Unit) {
    val evenOdd = remember(uppercase) { EvenOdd(uppercase) }
    Text(
        text = "Clicks: $counterValue ${evenOdd.check(counterValue)}",
        modifier = Modifier.clickable(onClick = onCounterClick)
    )
    Log.d(TAG, "ClickCounter(counter = $counterValue, uppercase = $uppercase), $evenOdd")
}