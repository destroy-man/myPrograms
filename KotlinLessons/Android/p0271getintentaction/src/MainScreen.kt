package ru.korobeynikov.p0271getintentaction

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Row {
        Button(onClick = {
            val intent = Intent("korobeynikov.showtime")
            context.startActivity(intent)
        }) {
            Text("Show time")
        }
        Button(onClick = {
            val intent = Intent("korobeynikov.showdate")
            context.startActivity(intent)
        }) {
            Text("Show date")
        }
    }
}