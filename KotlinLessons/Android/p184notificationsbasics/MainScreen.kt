package ru.korobeynikov.p184notificationsbasics

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.core.app.NotificationCompat
import java.util.concurrent.TimeUnit

@Composable
fun MainScreen() {
    //Уведомление с прогресс баром
    val context = LocalContext.current
    val channelId = "channel_id"

    val max = 100
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Some operation")
        .setContentText("Preparing")
        .setProgress(max, 0, true)

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, builder.build())

    Thread {
        try {
            TimeUnit.SECONDS.sleep(5)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        var progress = 0

        while (progress < max) {
            try {
                TimeUnit.MILLISECONDS.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            progress += 10
            builder.setProgress(max, progress, false).setContentText("$progress of $max")
            notificationManager.notify(1, builder.build())
        }
    }.start()
}

@Composable
fun NotificationLargeIconScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val bitmap = ImageBitmap.imageResource(android.R.drawable.ic_dialog_email).asAndroidBitmap()
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        //Добавляет иконку справа, иконка слева задается в манифесте в android:icon
        .setLargeIcon(bitmap)
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

@Composable
fun NotificationClickScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val resultIntent = Intent(context, MainActivity::class.java)
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
}

@Composable
fun DeleteAllNotificationScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    //1 уведомление
    var builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
    var notification = builder.build()

    var notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)

    //2 уведомление
    builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Title 2")
        .setContentText("Notification text 2")
    notification = builder.build()

    notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(2, notification)

    TimeUnit.SECONDS.sleep(5)
    notificationManager.cancelAll()
}

@Composable
fun DeleteNotificationScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)

    TimeUnit.SECONDS.sleep(5)
    notificationManager.cancel(1)
}

@Composable
fun FewNotificationsScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    //1 уведомление
    var builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
    var notification = builder.build()

    var notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)

    //2 уведомление
    builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Title 2")
        .setContentText("Notification text 2")
    notification = builder.build()

    notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(2, notification)
}

@Composable
fun UpdateNotificationScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    //Начальное уведомление
    var builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
    var notification = builder.build()

    var notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)

    //Обновленное уведомление
    TimeUnit.SECONDS.sleep(5)

    builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Title change")
        .setContentText("Notification text change")
    notification = builder.build()

    notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

@Composable
fun SimpleNotificationScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

private fun createNotificationChannel(channelId: String): NotificationChannel {
    val name = "my_channel_name"
    val descriptionText = "my_channel_description"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    return NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
    }
}