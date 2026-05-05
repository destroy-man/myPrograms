package ru.korobeynikov.p0311opensitegeodialer

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "http://developer.android.com".toUri()
                )
                context.startActivity(intent)
            }
        ) {
            Text("Web")
        }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            onClick = {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = "geo:55.754283,37.62002".toUri()
                context.startActivity(intent)
            }
        ) {
            Text("Map")
        }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = "tel:12345".toUri()
                context.startActivity(intent)
            }
        ) {
            Text("Call")
        }
    }
}