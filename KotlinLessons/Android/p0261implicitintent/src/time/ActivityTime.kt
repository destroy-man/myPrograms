package ru.korobeynikov.p0261implicitintent.time

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import ru.korobeynikov.p0261implicitintent.DateTimeScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityTime : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val dateTime = sdf.format(Date(System.currentTimeMillis()))
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                DateTimeScreen(dateTime)
            }
        }
    }
}