package ru.korobeynikov.p187notificationactionbuttonsreply.notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context.NOTIFICATION_SERVICE
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import ru.korobeynikov.p187notificationactionbuttonsreply.R
import ru.korobeynikov.p187notificationactionbuttonsreply.Utils

@Composable
fun NotificationScreen() {
    val activity = LocalActivity.current
    activity?.let {
        val intent = activity.intent
        var replyText: String? = null
        val results = RemoteInput.getResultsFromIntent(intent)
        results.let {
            replyText = results.getString(Utils.EXTRA_TEXT_REPLY)
        }

        val itemId = intent.getIntExtra(Utils.EXTRA_ITEM_ID, 0)

        val repliedNotification = NotificationCompat
            .Builder(activity, Utils.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Replied")
            .build()

        val notificationManager =
            activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            Utils.createNotificationChannel(Utils.CHANNEL_ID)
        )

        notificationManager.notify(itemId, repliedNotification)

        Text(replyText ?: "This is Notification Activity")
    }
}