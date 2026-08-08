package ru.korobeynikov.p186notificationadditionalstyles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.core.app.NotificationCompat
import androidx.core.app.Person

@Composable
fun MainScreen() {
    //Уведомление с дополнительными заголовками
    val context = LocalContext.current
    val channelId = "channel_id"

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setStyle(
            NotificationCompat.InboxStyle()
                .addLine("Line 1")
                .addLine("Line 2")
                .addLine("Line 3")
                .setBigContentTitle("Extended title")
                .setSummaryText("+5 more")
        )
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

@Composable
fun NotificationMessagingStyleScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val you = Person.Builder().setName("You").build()
    val ivan = Person.Builder().setName("Ivan").build()
    val andrey = Person.Builder().setName("Andrey").build()
    val messagingStyle = NotificationCompat.MessagingStyle(you)
    messagingStyle.setConversationTitle("Android chat")
        .addMessage("Всем привет!", System.currentTimeMillis(), ivan)
        .addMessage("Кто перешел на новую студию, как оно?", System.currentTimeMillis(), ivan)
        .addMessage("Я пока не переходил, жду отзывов", System.currentTimeMillis(), andrey)
        .addMessage("Я перешел", System.currentTimeMillis(), null as Person?)
        .addMessage(
            "Было несколько проблем, но все решаемо",
            System.currentTimeMillis(),
            null as Person?
        )
        .addMessage("Ок, спасибо!", System.currentTimeMillis(), ivan)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setStyle(messagingStyle)
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

@Composable
fun NotificationInboxStyleScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setStyle(
            NotificationCompat.InboxStyle()
                .addLine("Line 1")
                .addLine("Line 2")
                .addLine("Line 3")
        )
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

@Composable
fun NotificationBigPictureScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val bitmap = ImageBitmap.imageResource(R.mipmap.start).asAndroidBitmap()
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
    val notification = builder.build()

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(createNotificationChannel(channelId))

    notificationManager.notify(1, notification)
}

@Composable
fun NotificationBigTextScreen() {
    val context = LocalContext.current
    val channelId = "channel_id"

    val longText = "To have a notification appear in an expanded view, first create a " +
            "NotificationCompat.Builder object with the normal view options you want. Next, call " +
            "Builder.setStyle() with an expanded layout object as its argument."
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Title")
        .setContentText("Notification text")
        .setStyle(NotificationCompat.BigTextStyle().bigText(longText))
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