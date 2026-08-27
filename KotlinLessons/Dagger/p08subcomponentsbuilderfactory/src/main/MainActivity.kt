package ru.korobeynikov.p08subcomponentsbuilderfactory.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p08subcomponentsbuilderfactory.App
import ru.korobeynikov.p08subcomponentsbuilderfactory.di.MainComponent
import ru.korobeynikov.p08subcomponentsbuilderfactory.di.MainModule
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainComponentBuilder: MainComponent.Builder

    lateinit var mainActivityRepository: MainActivityRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Передача параметра в метод
        //val mainComponent = (application as App).appComponent.getMainComponent(MainModule())

        //Builder get метод
        //val mainComponent = (application as App).appComponent.getMainComponentBuilder().activity(this).build()

        //Factory get метод
        //val mainComponent = (application as App).appComponent.getMainComponentFactory().create(this)

        (application as App).appComponent.injectMainActivity(this)
        val mainComponent = mainComponentBuilder.activity(this).build()

        mainActivityRepository = mainComponent.getMainActivityRepository()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("main activity repository = ${mainActivityRepository.hashCode()}")
            }
        }
    }
}