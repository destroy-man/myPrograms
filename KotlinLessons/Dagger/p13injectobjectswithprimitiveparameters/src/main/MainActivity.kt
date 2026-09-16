package ru.korobeynikov.p13injectobjectswithprimitiveparameters.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.App
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.di.AppComponent.ServerApiFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var serverApiFactory: ServerApiFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)
        val serverApi = serverApiFactory.create("dev1.server.com")

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("server api: host = ${serverApi.host}, port: ${serverApi.port}")
            }
        }
    }
}