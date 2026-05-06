package ru.korobeynikov.p0321simplebrowser

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var url by remember { mutableStateOf("http://www.google.com") }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text("Site:")
            OutlinedTextField(
                url,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp),
                onValueChange = { newValue ->
                    url = newValue
                }
            )
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    url.toUri()
                )
            )
        }) {
            Text("Go to site")
        }
    }
}