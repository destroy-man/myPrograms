package ru.korobeynikov.lesson1_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val persons = listOf(Person("Alice"), Person("Bob", age = 29))
        val oldest = persons.maxBy { it.age ?: 0 }
        setContent {
            Text(text = "The oldest is: $oldest")
        }
    }
}