package ru.korobeynikov.p11scope.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p11scope.R
import ru.korobeynikov.p11scope.databinding.ActivityOrderBinding
import ru.korobeynikov.p11scope.di.OrderComponent

class OrderActivity : AppCompatActivity() {

    private val tag = "myLogs"
    private lateinit var orderComponent: OrderComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
        orderComponent = (application as App).appComponent.getOrderComponent()
        val orderRepository1 = orderComponent.getOrderRepository()
        val orderRepository2 = orderComponent.getOrderRepository()
        Log.d(tag, "orderRepository1 = $orderRepository1")
        Log.d(tag, "orderRepository2 = $orderRepository2")
    }
}