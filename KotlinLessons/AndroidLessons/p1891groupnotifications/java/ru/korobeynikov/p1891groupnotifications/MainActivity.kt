package ru.korobeynikov.p1891groupnotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val GROUP_KEY = "group"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sender 1").setContentText("Subject text 1").setGroup(GROUP_KEY)
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        var notification = mBuilder.build()
        notificationManager.notify(1, notification)
        mBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sender 2").setContentText("Subject text 2").setGroup(GROUP_KEY)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        notification = mBuilder.build()
        notificationManager.notify(2, notification)
        mBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sender 3").setContentText("Subject text 3").setGroup(GROUP_KEY)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        notification = mBuilder.build()
        notificationManager.notify(3, notification)
        mBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setSubText("user_mail.com").setGroup(GROUP_KEY).setGroupSummary(true)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        notification = mBuilder.build()
        notificationManager.notify(-100, notification)
    }
}