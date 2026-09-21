package ru.korobeynikov.p4entrypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p4entrypoint.connection.MyConnection
import ru.korobeynikov.p4entrypoint.di.DataEntryPoint
import ru.korobeynikov.p4entrypoint.di.DatabaseEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseEntryPoint = EntryPoints.get(this, DatabaseEntryPoint::class.java)
        val databaseHelper1 = databaseEntryPoint.getDatabaseHelper()

        val connection = MyConnection()
        val dataEntryPoint = EntryPoints.get(this, DataEntryPoint::class.java)
        dataEntryPoint.injectMyConnection(connection)
        val databaseHelper2 = connection.databaseHelper

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text(
                    "database helper 1 = ${databaseHelper1.hashCode()}, " +
                            "database helper 2 = ${databaseHelper2.hashCode()}"
                )
            }
        }
    }
}