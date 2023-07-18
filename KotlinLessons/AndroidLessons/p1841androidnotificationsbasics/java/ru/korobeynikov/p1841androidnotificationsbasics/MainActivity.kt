package ru.korobeynikov.p1841androidnotificationsbasics

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1841androidnotificationsbasics.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val channelId = "MyChannel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "My channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        val max = 100
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Some operation")
            .setContentText("Preparing").setProgress(max, 0, true)
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