package ru.korobeynikov.p03additionalfeatures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandler
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandlerType

class MainActivity : ComponentActivity() {

    //Lazy
    //lateinit var networkUtilsLazy: Lazy<NetworkUtils>

    //Provider
    //lateinit var networkUtilsProvider: Provider<NetworkUtils>

    //Named и Qualifier для get метода
    //lateinit var serverApi: ServerApi

    /*Named для inject метода
    @Inject
    @Named("prod")
    lateinit var serverApi: ServerApi
     */

    /*Qualifier для inject метода
    @Inject
    @Prod("2")
    lateinit var serverApi: ServerApi
     */

    //IntoSet
    //lateinit var eventHandlers: Set<EventHandler>

    //IntoMap с простым ключом
    //lateinit var eventHandlers: Map<String, EventHandler>

    lateinit var eventHandlers: Map<EventHandlerType, EventHandler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Lazy
        networkUtilsLazy = (application as App).appComponent.getNetworkUtils()
        val networkUtils1 = networkUtilsLazy.get()
        val networkUtils2 = networkUtilsLazy.get()
         */

        /*Provider
        networkUtilsProvider = (application as App).appComponent.getNetworkUtils()
        val networkUtils1 = networkUtilsProvider.get()
        val networkUtils2 = networkUtilsProvider.get()
         */

        //Named и Qualifier для get метода
        //serverApi = (application as App).appComponent.getServerApiProd()

        //Named и Qualifier для inject метода
        //(application as App).appComponent.injectMainActivity(this)

        //IntoSet и IntoMap
        eventHandlers = (application as App).appComponent.getEventHandlers()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                eventHandlers.entries.forEach { eventHandler ->
                    Text("key: ${eventHandler.key}, value = ${eventHandler.value}")
                }
            }
        }
    }
}