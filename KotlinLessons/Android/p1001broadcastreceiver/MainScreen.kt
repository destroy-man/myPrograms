package ru.korobeynikov.p1001broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen() {
    var isCharging by remember { mutableStateOf(false) }
    SystemBroadcastReceiver(Intent.ACTION_POWER_CONNECTED) {
        isCharging = true
    }
    SystemBroadcastReceiver(Intent.ACTION_POWER_DISCONNECTED) {
        isCharging = false
    }
    Text(
        text = if (isCharging) {
            stringResource(R.string.charging)
        } else {
            stringResource(R.string.not_charging)
        }
    )
}

@Composable
fun SystemBroadcastReceiver(
    systemAction: String,
    onSystemEvent: (intent: Intent?) -> Unit
) {
    val context = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)
    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                currentOnSystemEvent(intent)
            }
        }
        val filter = IntentFilter(systemAction)
        context.registerReceiver(receiver, filter)
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}