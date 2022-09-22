package ru.korobeynikov.p1871notificationsactionbuttonsreply

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

class MyService : Service() {

    private val ACTION_REPLY="reply"
    private val EXTRA_ITEM_ID="extra_item_id"

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(ACTION_REPLY==intent?.action){
            val itemId=intent.getIntExtra(EXTRA_ITEM_ID,0)
            val builder= NotificationCompat.Builder(baseContext).setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Replied")
            val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "notif"
                val channel = NotificationChannel(channelId, "Notification channel",
                    NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
                builder.setChannelId(channelId)
            }
            val repliedNotification=builder.build()
            notificationManager.notify(itemId,repliedNotification)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO()
    }
}