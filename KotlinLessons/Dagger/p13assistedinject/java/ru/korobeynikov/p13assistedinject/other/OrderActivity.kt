package ru.korobeynikov.p13assistedinject.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p13assistedinject.R
import ru.korobeynikov.p13assistedinject.databinding.ActivityOrderBinding
import ru.korobeynikov.p13assistedinject.di.OrderComponent

class OrderActivity : AppCompatActivity() {

    private lateinit var orderComponent: OrderComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
        orderComponent = (application as App).appComponent.getOrderComponentFactory().create(this)
        Log.d(MainActivity.TAG, "uiHelper from order = ${orderComponent.getUiHelper()}")
    }
}