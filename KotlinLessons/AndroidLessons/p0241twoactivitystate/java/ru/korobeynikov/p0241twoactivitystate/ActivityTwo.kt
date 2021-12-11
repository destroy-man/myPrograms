package ru.korobeynikov.p0241twoactivitystate

import android.app.Activity
import android.os.Bundle
import android.util.Log

class ActivityTwo : Activity() {

    val TAG="States"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two)

        Log.d(TAG,"ActivityTwo: onCreate()")
    }

    protected override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"ActivityTwo: onRestart()")
    }

    protected override fun onStart() {
        super.onStart()
        Log.d(TAG,"ActivityTwo: onStart()")
    }

    protected override fun onResume() {
        super.onResume()
        Log.d(TAG,"ActivityTwo: onResume()")
    }

    protected override fun onPause() {
        super.onPause()
        Log.d(TAG,"ActivityTwo: onPause()")
    }

    protected override fun onStop() {
        super.onStop()
        Log.d(TAG,"ActivityTwo: onStop()")
    }

    protected override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"ActivityTwo: onDestroy()")
    }
}