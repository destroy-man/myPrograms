package ru.korobeynikov.p0241twoactivitylifecycle

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun FirstScreen() {
    val context = LocalContext.current
    Column {
        Text("Hello world!")
        Button(onClick = {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Go to Second Activity")
        }
    }
}