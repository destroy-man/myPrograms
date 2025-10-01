package ru.korobeynikov.p18producerememberupdatedstatessideeffect

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    //SideEffect
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            Log.d(TAG, "HomeScreen log")
            SideEffect {
                Log.d(TAG, "HomeScreen log in SideEffect")
            }
            val a = 1 / 0
        }
    }
}

@Composable
fun HomeScreenRememberUpdatedState() {
    Column {
        var sliderPosition by remember { mutableFloatStateOf(1f) }
        Slider(
            value = sliderPosition,
            valueRange = 1f..10f,
            onValueChange = { sliderPosition = it }
        )
        TrackPosition(position = sliderPosition)
    }
}

@Composable
fun TrackPosition(position: Float) {
    val positionValue by rememberUpdatedState(newValue = position)
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            Log.d(TAG, "track position $positionValue")
        }
    }
}

@Composable
fun TrackPositionCustom(position: Float) {
    var positionValue by remember { mutableFloatStateOf(position) }
    positionValue = position
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            Log.d(TAG, "track position $positionValue")
        }
    }
}

@Composable
fun HomeScreenProduceState() {
    val scope = rememberCoroutineScope()
    val count by produceState(0) {
        val job = scope.launch {
            while (true) {
                delay(1000)
                value += 1
            }
        }
        awaitDispose {
            Log.d(TAG, "cancel coroutine")
            job.cancel()
        }
    }
    Text("count = $count")
}

@Composable
fun HomeScreenLaunchedEffect() {
    var count by remember { mutableIntStateOf(0) }
    Text("count = $count")
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }
}