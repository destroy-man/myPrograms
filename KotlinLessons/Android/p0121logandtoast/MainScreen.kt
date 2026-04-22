package ru.korobeynikov.p0121logandtoast

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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

private const val TAG = "myLogs"

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var text by remember { mutableStateOf("Text") }
    val onChangeText: (String) -> Unit = { buttonName ->
        text = "Нажата кнопка $buttonName"
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Text(text, modifier = Modifier.padding(bottom = 50.dp))
        Button(modifier = Modifier.width(100.dp), onClick = {
            val okPressString = "Нажата кнопка OK"
            Log.d(TAG, okPressString)
            Toast.makeText(context, okPressString, Toast.LENGTH_LONG).show()
            onChangeText("OK")
        }) {
            Text("OK")
        }
        Button(modifier = Modifier.width(100.dp), onClick = {
            val cancelPressString = "Нажата кнопка Cancel"
            Log.d(TAG, cancelPressString)
            Toast.makeText(context, cancelPressString, Toast.LENGTH_LONG).show()
            onChangeText("Cancel")
        }) {
            Text("Cancel")
        }
    }
}