package ru.korobeynikov.p1851notificationsactivityopeningmodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1851notificationsactivityopeningmodes.databinding.ActivityWhatsNewBinding

class WhatsNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityWhatsNewBinding>(this, R.layout.activity_whats_new)
    }
}