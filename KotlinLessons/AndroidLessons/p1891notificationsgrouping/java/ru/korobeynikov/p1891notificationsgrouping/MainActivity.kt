package ru.korobeynikov.p1891notificationsgrouping

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1891notificationsgrouping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val channelId = "MyChannel"
    private val groupKey = "user_mail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "My channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        for (i in 1..3) {
            val mBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Sender $i")
                .setContentText("Subject text $i").setGroup(groupKey)
            notificationManager.notify(i, mBuilder.build())
        }
        val mBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher).setContentInfo("user_mail.com").setGroup(groupKey)
            .setGroupSummary(true)
        notificationManager.notify(-100, mBuilder.build())
    }
}