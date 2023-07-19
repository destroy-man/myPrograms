package ru.korobeynikov.p1861extendednotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1861extendednotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val channelId = "MyChannel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "My channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title")
            .setContentText("Notification text").setStyle(NotificationCompat.InboxStyle()
                .addLine("Line 1").addLine("Line 2").addLine("Line 3")
                .setBigContentTitle("Extended title").setSummaryText("+5 more"))
        notificationManager.notify(1, builder.build())
    }
}