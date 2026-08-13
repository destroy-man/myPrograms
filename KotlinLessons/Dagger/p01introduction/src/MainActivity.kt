package ru.korobeynikov.p01introduction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject //Внедрение зависимости через inject метод
    lateinit var databaseHelper: DatabaseHelper

    @Inject //Внедрение зависимости через inject метод
    lateinit var networkUtils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Внедрение зависимости через get методы
        val appComponent = (application as App).appComponent

        databaseHelper = appComponent.getDatabaseHelper()
        networkUtils = appComponent.getNetworkUtils()
        */

        //Внедрение зависимости через inject метод
        (application as App).appComponent.injectMainActivity(this)

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text(
                    "database helper = ${databaseHelper.hashCode()}, " +
                            "network utils = ${networkUtils.hashCode()}"
                )
            }
        }
    }
}