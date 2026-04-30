package ru.korobeynikov.p0261implicitintent.date

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

class ActivityDate : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateTime = sdf.format(Date(System.currentTimeMillis()))
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                DateTimeScreen(dateTime)
            }
        }
    }
}