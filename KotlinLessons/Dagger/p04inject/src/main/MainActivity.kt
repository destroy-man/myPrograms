package ru.korobeynikov.p04inject.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p04inject.App
import ru.korobeynikov.p04inject.database.DatabaseHelper
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainActivityRepository: MainActivityRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("main activity repository = ${mainActivityRepository.hashCode()}")
            }
        }
    }

    @Inject //Метод выполнится при инжекте MainActivity
    fun getDatabaseHelper(databaseHelper: DatabaseHelper) {
        Log.d("myLogs", "database helper = ${databaseHelper.hashCode()}")
    }
}