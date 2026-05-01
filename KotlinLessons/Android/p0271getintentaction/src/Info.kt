package ru.korobeynikov.p0271getintentaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Info : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var format = ""
        var textInfo = ""
        intent.action?.let { action ->
            if (action == "korobeynikov.showtime") {
                format = "HH:mm:ss"
                textInfo = "Time: "
            } else if (action == "korobeynikov.showdate") {
                format = "dd.MM.yyyy"
                textInfo = "Date: "
            }
        }
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateTime = sdf.format(Date(System.currentTimeMillis()))

        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                InfoScreen("$textInfo$dateTime")
            }
        }
    }
}