package ru.korobeynikov.p09componentsdependencies.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p09componentsdependencies.App

class MainActivity : ComponentActivity() {

    lateinit var mainActivityRepository: MainActivityRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityRepository = (application as App).mainComponent.getMainActivityRepository()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("main activity repository = ${mainActivityRepository.hashCode()}")
            }
        }
    }
}