package com.korobeynikov.p0231oneactivitystate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG="States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"MainActivity: onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"MainActivity: onDestroy()")
    }
}