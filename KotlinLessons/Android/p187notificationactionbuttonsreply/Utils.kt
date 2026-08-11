package ru.korobeynikov.p187notificationactionbuttonsreply

import android.app.NotificationChannel
import android.app.NotificationManager

object Utils {
    const val CHANNEL_ID = "channel_id"
    const val EXTRA_ITEM_ID = "extra_item_id"
    const val EXTRA_TEXT_REPLY = "extra_text_reply"

    fun createNotificationChannel(channelId: String): NotificationChannel {
        val name = "my_channel_name"
        val descriptionText = "my_channel_description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
    }
}