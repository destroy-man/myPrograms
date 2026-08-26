package ru.korobeynikov.p07subcomponents.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p07subcomponents.App
import ru.korobeynikov.p07subcomponents.database.DatabaseHelper

class MainActivity : ComponentActivity() {

    lateinit var databaseHelper: DatabaseHelper

    lateinit var mainActivityRepository: MainActivityRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainComponent = (application as App).appComponent.getMainComponent()
        databaseHelper = mainComponent.getDatabaseHelper()
        mainActivityRepository = mainComponent.getMainActivityRepository()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text(
                    "main activity repository = ${mainActivityRepository.hashCode()}, " +
                            "database helper = ${databaseHelper.hashCode()}"
                )
            }
        }
    }
}