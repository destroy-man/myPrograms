package ru.korobeynikov.p12multiscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p12multiscope.di.order.OrderComponent
import javax.inject.Inject

class OrderActivity : ComponentActivity() {

    @Inject
    lateinit var orderFactory: OrderComponent.OrderFactory

    lateinit var orderComponent: OrderComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MultiScope для get-методов
        //orderComponent = (application as App).appComponent.getOrderFactory().create(this)

        (application as App).appComponent.injectOrderActivity(this)
        orderComponent=orderFactory.create(this)
        val uiHelperOrder=orderComponent.getUiHelper()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("This is Order Activity")
                Text("ui helper order: ${uiHelperOrder.hashCode()}")
            }
        }
    }
}