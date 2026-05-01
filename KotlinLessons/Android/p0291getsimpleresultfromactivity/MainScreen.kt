package ru.korobeynikov.p0291getsimpleresultfromactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(name: String, onOpenActivity: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp),
            onClick = onOpenActivity
        ) {
            Text("Input name")
        }
        Text("Your name is $name", Modifier.align(Alignment.CenterHorizontally))
    }
}