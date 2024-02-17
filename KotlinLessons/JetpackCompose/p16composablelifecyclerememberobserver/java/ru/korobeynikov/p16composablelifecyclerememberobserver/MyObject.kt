package ru.korobeynikov.p16composablelifecyclerememberobserver

import android.util.Log
import androidx.compose.runtime.RememberObserver

class MyObject : RememberObserver {

    companion object {
        const val TAG = "myLogs"
    }

    init {
        Log.d(TAG, "init ${this.hashCode().toString(16)}")
    }

    override fun onAbandoned() {
        Log.d(TAG, "onAbandoned ${this.hashCode().toString(16)}")
    }

    override fun onForgotten() {
        Log.d(TAG, "onForgotten ${this.hashCode().toString(16)}")
    }

    override fun onRemembered() {
        Log.d(TAG, "onRemembered ${this.hashCode().toString(16)}")
    }
}