package ru.korobeynikov.p1871notificationsactionbuttonsreply

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.RemoteInput
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (MainActivity.ACTION_REPLY == intent?.action) {
            var replyText: CharSequence? = null
            val results = RemoteInput.getResultsFromIntent(intent)
            if (results != null)
                replyText = results.getCharSequence(MainActivity.EXTRA_TEXT_REPLY)
            Toast.makeText(baseContext, replyText, Toast.LENGTH_SHORT).show()
            val itemId = intent.getIntExtra(MainActivity.EXTRA_ITEM_ID, 0)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(MainActivity.CHANNEL_ID, "My channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            val repliedNotification = NotificationCompat.Builder(baseContext,
                MainActivity.CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentText("Replied").build()
            notificationManager.notify(itemId, repliedNotification)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null
}