package ru.korobeynikov.p0971servicebindclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0971servicebindclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val logTag = "myLogs"
    var bound = false
    private lateinit var sConn: ServiceConnection
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        serviceIntent = Intent("ru.korobeynikov.p0972servicebindserver.MyService")
        serviceIntent.component = ComponentName("ru.korobeynikov.p0972servicebindserver",
            "ru.korobeynikov.p0972servicebindserver.MyService")
        sConn = object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(logTag, "MainActivity onServiceConnected")
                bound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(logTag, "MainActivity onServiceDisconnected")
                bound = false
            }
        }
    }

    fun clickStart() = applicationContext.startForegroundService(serviceIntent)

    fun clickStop() = stopService(serviceIntent)

    fun clickBind() = bindService(serviceIntent, sConn, 0)

    fun clickUnBind() {
        if (!bound) return
        unbindService(sConn)
        bound = false
    }

    override fun onDestroy() {
        super.onDestroy()
        clickUnBind()
    }
}