package ru.korobeynikov.p1841notificationsbasics

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val max = 100
        val builder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Some operation").setContentText("Preparing").setProgress(max, 0, true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }
        notificationManager.notify(1, builder.build())
        Thread {
            try {
                TimeUnit.SECONDS.sleep(3)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var progress = 0
            while (progress < max) {
                try {
                    TimeUnit.MILLISECONDS.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progress += 10
                builder.setProgress(max, progress, false).setContentText("$progress of $max")
                notificationManager.notify(1, builder.build())
            }
            builder.setProgress(0, 10, false).setContentText("Completed")
            notificationManager.notify(1, builder.build())
        }.start()
    }
}