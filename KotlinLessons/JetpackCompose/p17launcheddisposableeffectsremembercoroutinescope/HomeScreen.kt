package ru.korobeynikov.p17launcheddisposableeffectsremembercoroutinescope

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    //Scope out if
    Column {
        var checked by remember { mutableStateOf(true) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        val scope = rememberCoroutineScope()
        if (checked) {
            Text(text = "Click", modifier = Modifier.clickable {
                scope.launch {
                    var count = 0
                    while (true) {
                        Log.d(Utils.TAG, "count = ${count++}")
                        delay(1000)
                    }
                }
            })
        }
    }
}

@Composable
fun HomeScreenScopeInIf() {
    Column {
        var checked by remember { mutableStateOf(true) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            val scope = rememberCoroutineScope()
            Text(text = "Click", modifier = Modifier.clickable {
                scope.launch {
                    var count = 0
                    while (true) {
                        Log.d(Utils.TAG, "count = ${count++}")
                        delay(1000)
                    }
                }
            })
        }
    }
}

@Composable
fun HomeScreenDisposableEffect(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                onStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                onStop()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun HomeScreenLaunchedEffect() {
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            LaunchedEffect(key1 = Unit) {
                var count = 0
                while (true) {
                    Log.d(Utils.TAG, "count = ${count++}")
                    delay(1000)
                }
            }
        }
    }
}