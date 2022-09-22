package ru.korobeynikov.p1881customnotifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {
    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootIntent = Intent(this, MainActivity::class.java)
        rootIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val rootPendingIntent = PendingIntent.getActivity(this, 0, rootIntent, 0)
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.textView, "Custom notification text")
        remoteViews.setOnClickPendingIntent(R.id.root, rootPendingIntent)
        val remoteViewsExtended = RemoteViews(packageName, R.layout.extended_notification)
        remoteViewsExtended.setTextViewText(R.id.textView, "Extended custom notification text")
        remoteViewsExtended.setOnClickPendingIntent(R.id.root, rootPendingIntent)
        val builder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setCustomContentView(remoteViews).setCustomBigContentView(remoteViewsExtended)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }
        val notification = builder.build()
        notificationManager.notify(1, notification)
    }
}