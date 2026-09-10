package ru.korobeynikov.p11scope

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
import ru.korobeynikov.p11scope.order.OrderActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            Column(modifier = Modifier.safeContentPadding()) {
                Text("This is Main Activity")
                Button(onClick = {
                    val intent = Intent(context, OrderActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Go to Order")
                }
            }
        }
    }
}