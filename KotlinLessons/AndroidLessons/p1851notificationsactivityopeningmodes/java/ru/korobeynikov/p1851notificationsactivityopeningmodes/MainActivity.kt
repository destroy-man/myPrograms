package ru.korobeynikov.p1851notificationsactivityopeningmodes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultIntent = Intent(this, WhatsNewActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0)
        val builder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Title").setContentText("Notification text")
            .setContentIntent(resultPendingIntent)
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