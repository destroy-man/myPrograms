package ru.korobeynikov.p21materialdesigntheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import ru.korobeynikov.p21materialdesigntheme.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                HomeScreen()
            }
            /* Custom theme
            MaterialTheme(colorScheme = lightColorScheme(primary = Color.Green)) {
                HomeScreen()
            }
             */
        }
    }
}