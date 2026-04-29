package ru.korobeynikov.p0231oneactivitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {

    val tag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen()
            }
        }
        Log.d(tag, "MainActivity: onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "MainActivity: onDestroy()")
    }
}