package ru.korobeynikov.p1basics.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.korobeynikov.p1basics.order.OrderActivity
import ru.korobeynikov.p1basics.user.UserActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            Row(modifier = Modifier.safeContentPadding()) {
                Button(onClick = {
                    val intent = Intent(context, OrderActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Go to Order")
                }
                Button(onClick = {
                    val intent = Intent(context, UserActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Go to User")
                }
            }
        }
    }
}