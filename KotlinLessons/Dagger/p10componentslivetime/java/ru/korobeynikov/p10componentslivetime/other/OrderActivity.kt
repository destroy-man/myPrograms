package ru.korobeynikov.p10componentslivetime.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10componentslivetime.R
import ru.korobeynikov.p10componentslivetime.databinding.ActivityOrderBinding
import ru.korobeynikov.p10componentslivetime.di.OrderComponent

class OrderActivity : AppCompatActivity() {

    private lateinit var orderComponent: OrderComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
        orderComponent = (application as App).appComponent.getOrderComponent()
    }
}