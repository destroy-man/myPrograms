package ru.korobeynikov.p0281intentextras

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

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Input your Name",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("First Name")
                OutlinedTextField(
                    firstName,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    onValueChange = { newValue ->
                        firstName = newValue
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Last Name")
                OutlinedTextField(
                    lastName,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    onValueChange = { newValue ->
                        lastName = newValue
                    }
                )
            }
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
            val intent = Intent(context, ViewActivity::class.java)
            intent.putExtra("firstName", firstName)
            intent.putExtra("lastName", lastName)
            context.startActivity(intent)
        }) {
            Text("Submit")
        }
    }
}