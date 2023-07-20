package ru.korobeynikov.p1871notificationsactionbuttonsreply

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1871notificationsactionbuttonsreply.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTION_REPLY = "ru.korobeynikov.notifications.action_delete"
        const val EXTRA_ITEM_ID = "extra_item"
        const val EXTRA_TEXT_REPLY = "extra_text"
        const val CHANNEL_ID = "MyChannel"
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val itemId = 1
        val serviceIntent = Intent(this, MyService::class.java)
        serviceIntent.action = ACTION_REPLY
        serviceIntent.putExtra(EXTRA_ITEM_ID, itemId)
        val replyPendingIntent = PendingIntent.getService(applicationContext, itemId,
            serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteInput = RemoteInput.Builder(EXTRA_TEXT_REPLY).setLabel("Type message").build()
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
            "Reply", replyPendingIntent).addRemoteInput(remoteInput).build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title")
            .setContentText("Notification text").addAction(action)
        notificationManager.notify(itemId, builder.build())
    }
}