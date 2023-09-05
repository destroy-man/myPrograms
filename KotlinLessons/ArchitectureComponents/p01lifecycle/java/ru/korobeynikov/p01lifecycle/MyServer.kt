package ru.korobeynikov.p01lifecycle

import android.util.Log
import androidx.lifecycle.*

class MyServer : DefaultLifecycleObserver, LifecycleEventObserver {

    private val logTag = "myLogs"

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        connect()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        disconnect()
    }

    private fun connect() = Log.d(logTag, "connect")

    private fun disconnect() = Log.d(logTag, "disconnect")

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(logTag, "source = $source, event = $event")
    }
}