package ru.korobeynikov.p0231oneactivitystate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0231oneactivitystate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Log.d(tag, "MainActivity: onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "MainActivity: onDestroy()")
    }
}