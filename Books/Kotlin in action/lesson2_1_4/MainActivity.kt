package ru.korobeynikov.lesson2_1_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloWorld(args = arrayOf("Bob"))
        }
    }
}

@Composable
fun HelloWorld(args: Array<String>) {
    Text(text = "Hello, ${if (args.isNotEmpty()) args[0] else "someone"}!")
}