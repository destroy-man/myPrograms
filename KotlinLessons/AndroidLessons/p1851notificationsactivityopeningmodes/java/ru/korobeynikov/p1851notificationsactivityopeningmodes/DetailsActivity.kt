package ru.korobeynikov.p1851notificationsactivityopeningmodes

import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DetailsActivity : AppCompatActivity() {

    private val EXTRA_ITEM_ID = "extra_item_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val itemId = intent.getLongExtra(EXTRA_ITEM_ID, 0)
        val notificationId = itemId.toInt()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }
}