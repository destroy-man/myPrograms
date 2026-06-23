package ru.korobeynikov.p1191alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val am = context.getSystemService("alarm") as AlarmManager
    var intent1: Intent
    var pIntent1: PendingIntent
    var intent2: Intent
    var pIntent2: PendingIntent? = null
    Column {
        Button(onClick = {
            intent1 = createIntent(context, "action 1", "extra 1")
            pIntent1 = PendingIntent.getBroadcast(
                context,
                0,
                intent1,
                PendingIntent.FLAG_IMMUTABLE
            )

            intent2 = createIntent(context, "action 2", "extra 2")
            pIntent2 = PendingIntent.getBroadcast(
                context,
                0,
                intent2,
                PendingIntent.FLAG_IMMUTABLE
            )
            Log.d(Constants.LOG_TAG, "start")
            am.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pIntent1)
            am.setRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 3000,
                60000,
                pIntent2
            )
        }) {
            Text(stringResource(R.string.button1))
        }
        Button(onClick = {
            pIntent2?.let {
                am.cancel(pIntent2)
            }
        }) {
            Text(stringResource(R.string.button2))
        }
    }
}

fun createIntent(context: Context, action: String, extra: String): Intent {
    val intent = Intent(context, Receiver::class.java)
    intent.action = action
    intent.putExtra("extra", extra)
    return intent
}