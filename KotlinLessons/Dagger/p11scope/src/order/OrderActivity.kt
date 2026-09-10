package ru.korobeynikov.p11scope.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import ru.korobeynikov.p11scope.App

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderComponent = (application as App).appComponent.getOrderComponent()
        val orderRepository1 = orderComponent.getOrderRepository()
        val orderRepository2 = orderComponent.getOrderRepository()

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("This is Order Activity")
                Text("order repository 1: ${orderRepository1.hashCode()}")
                Text("order repository 2: ${orderRepository2.hashCode()}")
            }
        }
    }
}