package ru.korobeynikov.p0241twoactivitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                SecondScreen()
            }
        }
        Log.d(Utils.TAG, "SecondActivity: onCreate()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(Utils.TAG, "SecondActivity: onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(Utils.TAG, "SecondActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(Utils.TAG, "SecondActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Utils.TAG, "SecondActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Utils.TAG, "SecondActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Utils.TAG, "SecondActivity: onDestroy()")
    }
}