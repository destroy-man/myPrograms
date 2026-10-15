package ru.korobeynikov.p01lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myServer = MyServer()
        lifecycle.addObserver(myServer)
        setContent {
            if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                Log.d("myLogs", "current state activity = ${lifecycle.currentState}")
            }
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("myLogs", "current state is at least = ${lifecycle.currentState}")
            }
            Column(modifier = Modifier.safeContentPadding()) {
                Text("myServer = $myServer")
            }
        }
    }
}