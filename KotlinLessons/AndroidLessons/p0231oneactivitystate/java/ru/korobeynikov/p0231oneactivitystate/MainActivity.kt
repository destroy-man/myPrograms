package ru.korobeynikov.p0231oneactivitystate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG="States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"MainActivity: onCreate()")
    }

    protected override fun onStart() {
        super.onStart()
        Log.d(TAG,"MainActivity: onStart()")
    }

    protected override fun onResume() {
        super.onResume()
        Log.d(TAG,"MainActivity: onResume()")
    }

    protected override fun onPause() {
        super.onPause()
        Log.d(TAG,"MainActivity: onPause()")
    }

    protected override fun onStop() {
        super.onStop()
        Log.d(TAG,"MainActivity: onStop()")
    }

    protected override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"MainActivity: onDestroy()")
    }
}