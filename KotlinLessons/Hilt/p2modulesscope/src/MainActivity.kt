package ru.korobeynikov.p2modulesscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p2modulesscope.database.DatabaseHelper
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var utils: Utils

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text(
                    "${utils.javaClass.simpleName} = ${utils.hashCode()}, " +
                            "${databaseHelper.javaClass.simpleName} = ${databaseHelper.hashCode()}"
                )
            }
        }
    }
}