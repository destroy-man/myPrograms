package ru.korobeynikov.p17launcheddisposableeffectsremembercoroutinescope

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
            /*
            HomeScreenDisposableEffect(
                onStart = {
                    Log.d(Utils.TAG, "onStart")
                },
                onStop = {
                    Log.d(Utils.TAG, "onStop")
                }
            )
             */
        }
    }
}