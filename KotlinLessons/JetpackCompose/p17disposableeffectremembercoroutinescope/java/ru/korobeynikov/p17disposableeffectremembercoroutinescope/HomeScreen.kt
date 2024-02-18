package ru.korobeynikov.p17disposableeffectremembercoroutinescope

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "myLogs"

@Composable
fun HomeScreen() = Column {
    var checked by remember {
        mutableStateOf(true)
    }
    Checkbox(checked = checked, onCheckedChange = { checked = it })
    val scope = rememberCoroutineScope()
    if (checked) {
        Text(text = "Click", modifier = Modifier.clickable {
            scope.launch {
                var count = 0
                while (true) {
                    Log.d(TAG, "count = ${count++}")
                    delay(1000)
                }
            }
        })
    }
}