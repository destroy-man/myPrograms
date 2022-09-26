package ru.korobeynikov.p01lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle

class MainActivity : AppCompatActivity() {

    private val myServer = MyServer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            myServer.onAny(myServer, Lifecycle.Event.ON_START)
    }
}