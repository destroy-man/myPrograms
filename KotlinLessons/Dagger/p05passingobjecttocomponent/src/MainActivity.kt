package ru.korobeynikov.p05passingobjecttocomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p05passingobjecttocomponent.database.DatabaseHelper
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var utils: Utils

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("utils = $utils, database helper = $databaseHelper, context = ${databaseHelper.context}")
            }
        }
    }
}