package ru.korobeynikov.p1871notificationsactionbuttonsreply

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.RemoteInput

class MainActivity : AppCompatActivity() {

    private val ACTION_REPLY = "reply"
    private val EXTRA_ITEM_ID = "extra_item_id"
    private val EXTRA_TEXT_REPLY = "extra_text_reply"

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemId = 1
        val intent = Intent(this, MyService::class.java)
        intent.action = ACTION_REPLY
        intent.putExtra(EXTRA_ITEM_ID, itemId)
        val replyPendingIntent = PendingIntent.getService(applicationContext, itemId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteInput = RemoteInput.Builder(EXTRA_TEXT_REPLY).setLabel("Type message").build()
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
            "Reply", replyPendingIntent).addRemoteInput(remoteInput).build()
        val builder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Title").setContentText("Notification text").addAction(action)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notif"
            val channel = NotificationChannel(channelId, "Notification channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }
        val notification = builder.build()
        notificationManager.notify(itemId, notification)
    }
}