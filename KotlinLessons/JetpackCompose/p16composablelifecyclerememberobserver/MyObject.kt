package ru.korobeynikov.p16composablelifecyclerememberobserver

import android.util.Log
import androidx.compose.runtime.RememberObserver

const val TAG = "myLogs"

class MyObject : RememberObserver {

    init {
        Log.d(TAG, "init ${this.hashCode().toHexString()}")
    }

    override fun onAbandoned() {
        Log.d(TAG, "onAbandoned ${this.hashCode().toHexString()}")
    }

    override fun onForgotten() {
        Log.d(TAG, "onForgotten ${this.hashCode().toHexString()}")
    }

    override fun onRemembered() {
        Log.d(TAG, "onRemembered ${this.hashCode().toHexString()}")
    }
}