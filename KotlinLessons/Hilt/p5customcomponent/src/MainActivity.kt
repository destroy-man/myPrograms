package ru.korobeynikov.p5customcomponent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                val context = LocalContext.current
                Text("This is main activity")
                Button(onClick = {
                    val intent = Intent(context, RootActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("Go to Root")
                }
            }
        }
    }
}