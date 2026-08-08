package ru.korobeynikov.p185notificationsactivityopeningmodes.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import ru.korobeynikov.p185notificationsactivityopeningmodes.details.DetailsActivity
import ru.korobeynikov.p185notificationsactivityopeningmodes.R
import ru.korobeynikov.p185notificationsactivityopeningmodes.whatsnew.WhatsNewActivity

@Composable
fun MainScreen() {
    //Активити в отдельном таске
    val context = LocalContext.current
    val channelId = "channel_id"

    val resultIntent = Intent(context, WhatsNewActivity::class.java)
    resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val resultPendingIntent = PendingIntent.getActivity(
        context,
        0,
        resultIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setContentIntent(resultPendingIntent)
        .setAutoCancel(true)
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)

    Text("This is Main Activity")
}

@Composable
fun TwoRelatedActivitiesScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"
    val extraItemId = "extra_item_id"
    val itemId = 12345678910L
    val notificationId = itemId.toInt()

    val resultIntent = Intent(context, DetailsActivity::class.java)
    resultIntent.putExtra(extraItemId, itemId)

    val stackBuilder = TaskStackBuilder.create(context)
    stackBuilder.addParentStack(DetailsActivity::class.java)
    stackBuilder.addNextIntent(resultIntent)

    val resultPendingIntent =
        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setContentIntent(resultPendingIntent)
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(notificationId, notification)

    Text("This is Main Activity")
}

private fun createNotificationChannel(channelId: String): NotificationChannel {
    val name = "my_channel_name"
    val descriptionText = "my_channel_description"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    return NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
    }
}