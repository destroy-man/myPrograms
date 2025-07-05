package ru.korobeynikov.p09remember

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    counter: State<Int>,
    onCounterClick: () -> Unit,
    uppercase: State<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
) {
    val counterValue = counter.value
    val uppercaseValue = uppercase.value
    Column {
        ClickCounter(
            counterValue = counterValue,
            onCounterClick = onCounterClick,
            uppercase = uppercaseValue
        )
        CheckboxWithText(
            uppercaseValue = uppercaseValue,
            text = "Uppercase",
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun ClickCounter(uppercase: Boolean, counterValue: Int, onCounterClick: () -> Unit) {
    val evenOdd = remember(uppercase) { EvenOdd(uppercase) }
    Text(
        text = "Clicks: $counterValue ${evenOdd.check(counterValue)}",
        modifier = Modifier.clickable(onClick = onCounterClick)
    )
    Log.d("myLogs", "ClickCounter(counter = $counterValue, uppercase = $uppercase), $evenOdd")
}

@Composable
fun CheckboxWithText(uppercaseValue: Boolean, text: String, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        onCheckedChange(!uppercaseValue)
    }) {
        Checkbox(
            checked = uppercaseValue,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .padding(2.dp),
            onCheckedChange = null
        )
        Text(text = text)
    }
}