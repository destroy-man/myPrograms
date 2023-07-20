package ru.korobeynikov.p1881customnotifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1881customnotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val channelId = "MyChannel"

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val rootIntent = Intent(this, MainActivity::class.java)
        val rootPendingIntent = PendingIntent.getActivity(applicationContext, 0, rootIntent, 0)
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.textView, "Custom notification text")
        remoteViews.setOnClickPendingIntent(R.id.root, rootPendingIntent)
        val remoteViewsExtended = RemoteViews(packageName, R.layout.extended_notification)
        remoteViewsExtended.setTextViewText(R.id.textView, "Extended custom notification text")
        remoteViewsExtended.setOnClickPendingIntent(R.id.root, rootPendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "My channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher).setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViewsExtended)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        notificationManager.notify(1, builder.build())
    }
}