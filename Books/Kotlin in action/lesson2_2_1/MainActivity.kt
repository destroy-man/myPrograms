package ru.korobeynikov.lesson2_2_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val person = Person("Bob", true)
            Column {
                Text(text = person.name)
                Text(text = "${person.isMarried}")
            }
        }
    }
}