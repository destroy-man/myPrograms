package ru.korobeynikov.p01lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MyServer : DefaultLifecycleObserver, LifecycleEventObserver {

    /*Методы DefaultLifecycleObserver
    override fun onStart(owner: LifecycleOwner) {
        connect()
    }

    override fun onStop(owner: LifecycleOwner) {
        disconnect()
    }
     */

    //Метод LifecycleEventObserver
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> connect()
            Lifecycle.Event.ON_STOP -> disconnect()
            else -> {
                Log.d("myLogs", "${event}: neither connect or disconnect")
            }
        }
    }

    fun connect() {
        Log.d("myLogs", "connect to server")
    }

    fun disconnect() {
        Log.d("myLogs", "disconnect from server")
    }
}