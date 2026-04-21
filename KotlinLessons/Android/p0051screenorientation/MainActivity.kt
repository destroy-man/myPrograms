package ru.korobeynikov.p0051screenorientation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val configuration = LocalConfiguration.current
            Column(modifier = Modifier.safeContentPadding()) {
                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    VerticalOrientationScreen()
                } else {
                    HorizontalOrientationScreen()
                }
            }
        }
    }
}