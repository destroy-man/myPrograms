package ru.korobeynikov.p187notificationactionbuttonsreply.main

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import ru.korobeynikov.p187notificationactionbuttonsreply.notification.NotificationActivity
import ru.korobeynikov.p187notificationactionbuttonsreply.Utils

@Composable
fun MainScreen() {
    //Уведомление со строкой ввода
    val context = LocalContext.current

    val itemId = 1

    val intent = Intent(context, NotificationActivity::class.java)
    intent.putExtra(Utils.EXTRA_ITEM_ID, itemId)
    val replyPendingIntent = PendingIntent.getActivity(
        context,
        itemId,
        intent,
        PendingIntent.FLAG_MUTABLE
    )

    val remoteInput = RemoteInput.Builder(Utils.EXTRA_TEXT_REPLY).setLabel("Type message").build()

    val action = NotificationCompat.Action
        .Builder(android.R.drawable.ic_menu_send, "Reply", replyPendingIntent)
        .addRemoteInput(remoteInput)
        .build()

    val builder = NotificationCompat.Builder(context, Utils.CHANNEL_ID)
        .setSmallIcon(ru.korobeynikov.p187notificationactionbuttonsreply.R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .addAction(action)
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(
        Utils.createNotificationChannel(Utils.CHANNEL_ID)
    )

    notificationManager.notify(1, notification)

    Text("This is Main Activity")
}

@Composable
fun NotificationButtonScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val notificationIntent = Intent(context, NotificationActivity::class.java)
    val notificationPendingIntent = PendingIntent.getActivity(
        context,
        0,
        notificationIntent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(ru.korobeynikov.p187notificationactionbuttonsreply.R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .addAction(
            android.R.drawable.ic_notification_overlay,
            "Notification",
            notificationPendingIntent
        )
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(Utils.createNotificationChannel(channelId))

    notificationManager.notify(1, notification)

    Text("This is Main Activity")
}