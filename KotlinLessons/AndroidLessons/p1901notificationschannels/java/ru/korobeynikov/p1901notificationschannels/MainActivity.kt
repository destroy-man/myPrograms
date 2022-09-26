package ru.korobeynikov.p1901notificationschannels

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID = "notif"
        private const val GROUP_ID1 = "userA"
        private const val GROUP_ID2 = "userB"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(GROUP_ID1,
            "User A (group)"))
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(GROUP_ID2,
            "User B (group)"))
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title").setContentText("Notification text")
        var channel = NotificationChannel(CHANNEL_ID, "Mail", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "My channel description"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(false)
        channel.group = GROUP_ID1
        notificationManager.createNotificationChannel(channel)
        builder.setChannelId(CHANNEL_ID)
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
        channel = NotificationChannel("$CHANNEL_ID 1", "Events", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "My channel description"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(false)
        channel.group = GROUP_ID1
        notificationManager.createNotificationChannel(channel)
        builder.setChannelId("$CHANNEL_ID 1")
        channel = NotificationChannel("$CHANNEL_ID 2", "Mail", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "My channel description"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(false)
        channel.group = GROUP_ID2
        notificationManager.createNotificationChannel(channel)
        builder.setChannelId("$CHANNEL_ID 2")
        channel = NotificationChannel("$CHANNEL_ID 3", "Events", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "My channel description"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(false)
        channel.group = GROUP_ID2
        notificationManager.createNotificationChannel(channel)
        builder.setChannelId("$CHANNEL_ID 3")
        val notification = builder.build()
        notificationManager.notify(1, notification)
    }
}