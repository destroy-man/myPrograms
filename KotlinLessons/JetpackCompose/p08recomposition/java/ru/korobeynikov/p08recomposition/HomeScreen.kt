package ru.korobeynikov.p08recomposition

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

const val TAG = "myLogs"

@Composable
fun HomeScreen(counter: State<Int>, onCounterClick: () -> Unit) {
    val counterValue = counter.value
    Log.d(TAG, "HomeScreen")
    Column {
        ClickCounter(counterValue = counterValue, onCounterClick = onCounterClick)
        InfoText(text = if (counterValue < 3) "More" else "Enough")
    }
}

@Composable
fun ClickCounter(counterValue: Int, onCounterClick: () -> Unit) {
    Log.d(TAG, "ClickCounter $counterValue")
    Text(text = "Clicks: $counterValue", modifier = Modifier.clickable {
        Log.d(TAG, "--- click ---")
        onCounterClick()
    })
}

@Composable
fun InfoText(text: String) {
    Log.d(TAG, "InfoText $text")
    Text(text = text, fontSize = 24.sp)
}