package ru.korobeynikov.p0981servicebindinglocal

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0981servicebindinglocal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    private var interval = 0L
    var bound = false
    private lateinit var sConn: ServiceConnection
    private lateinit var serviceIntent: Intent
    private lateinit var tvInterval: TextView
    lateinit var myService: MyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvInterval = binding.tvInterval
        serviceIntent = Intent(this, MyService::class.java)
        sConn = object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected")
                myService = (binder as MyService.MyBinder).getService()
                bound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected")
                bound = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(serviceIntent, sConn, 0)
    }

    override fun onStop() {
        super.onStop()
        if (!bound) return
        unbindService(sConn)
        bound = false
    }

    fun clickStart() = startService(serviceIntent)

    fun clickUp() {
        if (!bound) return
        interval = myService.upInterval(500)
        tvInterval.text = getString(R.string.interval_value, interval)
    }

    fun clickDown() {
        if (!bound) return
        interval = myService.downInterval(500)
        tvInterval.text = getString(R.string.interval_value, interval)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "MainActivity onDestroy")
        stopService(serviceIntent)
    }
}