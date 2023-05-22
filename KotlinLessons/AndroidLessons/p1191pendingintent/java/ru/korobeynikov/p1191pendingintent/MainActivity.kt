package ru.korobeynikov.p1191pendingintent

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1191pendingintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    private lateinit var am: AlarmManager
    private lateinit var intent1: Intent
    private lateinit var intent2: Intent
    private lateinit var pIntent1: PendingIntent
    private var pIntent2: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        am = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun clickButton1() {
        intent1 = createIntent("action", "extra 1")
        pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0)
        intent2 = createIntent("action", "extra 2")
        pIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0)
        Log.d(LOG_TAG, "start")
        am.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pIntent1)
        am.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pIntent2)
    }

    fun clickButton2() = am.cancel(pIntent2)

    private fun createIntent(action: String, extra: String) =
        with(Intent(this, Receiver::class.java)) {
            this.action = action
            putExtra("extra", extra)
            this
        }
}