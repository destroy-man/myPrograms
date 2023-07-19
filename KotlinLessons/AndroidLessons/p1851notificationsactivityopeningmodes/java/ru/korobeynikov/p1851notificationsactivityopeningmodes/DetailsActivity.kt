package ru.korobeynikov.p1851notificationsactivityopeningmodes

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1851notificationsactivityopeningmodes.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
        val itemId = intent.getLongExtra(MainActivity.EXTRA_ITEM_ID, 0)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(itemId.toInt())
    }
}