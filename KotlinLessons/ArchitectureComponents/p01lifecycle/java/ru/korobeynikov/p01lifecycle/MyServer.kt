package ru.korobeynikov.p01lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class MyServer : LifecycleObserver {

    private val logTag = "myLogs"

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    private fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START)
            Log.d(logTag, "connect server")
        else if (event == Lifecycle.Event.ON_STOP)
            Log.d(logTag, "disconnect server")
        if (source.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            Log.d(logTag, "state: ${source.lifecycle.currentState}")
    }
}