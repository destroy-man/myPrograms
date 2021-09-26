package com.korobeynikov.p1041fragmentlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOG_TAG,"MainActivity onCreate")
    }

    protected override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG,"MainActivity onStart")
    }

    protected override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG,"MainActivity onResume")
    }

    protected override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG,"MainActivity onPause")
    }

    protected override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG,"MainActivity onStop")
    }

    protected override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"MainActivity onDestroy")
    }
}