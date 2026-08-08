package ru.korobeynikov.p185notificationsactivityopeningmodes.details

import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.p185notificationsactivityopeningmodes.Constants

@Composable
fun DetailsScreen() {
    val activity = LocalActivity.current

    activity?.let {
        val itemId = activity.intent.getLongExtra(Constants.EXTRA_ITEM_ID, 0)
        val notificationId = itemId.toInt()

        val notificationManager =
            activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    Text("This is Details Activity")
}