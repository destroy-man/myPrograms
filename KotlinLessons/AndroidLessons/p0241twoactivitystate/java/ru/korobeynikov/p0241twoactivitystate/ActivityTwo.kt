package ru.korobeynikov.p0241twoactivitystate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0241twoactivitystate.databinding.ActivityTwoBinding

class ActivityTwo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityTwoBinding>(this, R.layout.activity_two)
        Log.d(MainActivity.TAG, "ActivityTwo: onCreate()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(MainActivity.TAG, "ActivityTwo: onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(MainActivity.TAG, "ActivityTwo: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(MainActivity.TAG, "ActivityTwo: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(MainActivity.TAG, "ActivityTwo: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(MainActivity.TAG, "ActivityTwo: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MainActivity.TAG, "ActivityTwo: onDestroy()")
    }
}