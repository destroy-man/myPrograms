package ru.korobeynikov.p189notificationgroup

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"
    val groupKey = "group_key"

    //Уведомления, входящие в группу
    for (i in 1..3) {
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sender $i")
            .setContentText("Subject text $i")
            .setGroup(groupKey)
        val notification = mBuilder.build()

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(createNotificationChannel(channelId))

        notificationManager.notify(i, notification)
    }

    //Уведомление-группа
    val mBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setGroup(groupKey)
        .setGroupSummary(true)
    val notification = mBuilder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(-100, notification)
}

private fun createNotificationChannel(channelId: String): NotificationChannel {
    val name = "my_channel_name"
    val descriptionText = "my_channel_description"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    return NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
    }
}