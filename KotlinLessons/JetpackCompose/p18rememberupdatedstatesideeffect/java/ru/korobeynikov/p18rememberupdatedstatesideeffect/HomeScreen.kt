package ru.korobeynikov.p18rememberupdatedstatesideeffect

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

const val TAG = "myLogs"

@Composable
fun HomeScreen() = Column {
    var checked by remember {
        mutableStateOf(false)
    }
    Checkbox(checked = checked, onCheckedChange = { checked = it })
    if (checked) {
        Log.d(TAG, "HomeScreen log")
        SideEffect {
            Log.d(TAG, "HomeScreen log in SideEffect")
        }
        val a = 1 / 0
    }
}