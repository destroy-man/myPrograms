package ru.korobeynikov.p1851notificationsactivityopeningmodes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1851notificationsactivityopeningmodes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM_ID = "extra"
    }

    private val channelId = "MyChannel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val itemId = 12345678910L
        //Pending intent
        val resultIntent = Intent(this, WhatsNewActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        resultIntent.putExtra(EXTRA_ITEM_ID, itemId)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(WhatsNewActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        //Notification
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "My channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title")
            .setContentText("Notification text").setContentIntent(resultPendingIntent)
        notificationManager.notify(itemId.toInt(), builder.build())
    }
}