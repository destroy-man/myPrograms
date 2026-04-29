package ru.korobeynikov.p0241twoactivitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier

class FirstActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                FirstScreen()
            }
        }
        Log.d(Utils.TAG, "FirstActivity: onCreate()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(Utils.TAG, "FirstActivity: onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(Utils.TAG, "FirstActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(Utils.TAG, "FirstActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Utils.TAG, "FirstActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Utils.TAG, "FirstActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Utils.TAG, "FirstActivity: onDestroy()")
    }
}