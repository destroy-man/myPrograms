package ru.korobeynikov.p0701saveactivitystate

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {

    val logTag = "myLogs"
    var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen {
                    Toast.makeText(context, "Count = ${++cnt}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.d(logTag, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(logTag, "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d(logTag, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(logTag, "onRestart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        cnt = savedInstanceState.getInt("count")
        Log.d(logTag, "onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.d(logTag, "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", cnt)
        Log.d(logTag, "onSaveInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(logTag, "onStop")
    }
}