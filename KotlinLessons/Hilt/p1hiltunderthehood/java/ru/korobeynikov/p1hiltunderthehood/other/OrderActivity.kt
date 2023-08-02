package ru.korobeynikov.p1hiltunderthehood.other

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1hiltunderthehood.R
import ru.korobeynikov.p1hiltunderthehood.databinding.ActivityOrderBinding
import javax.inject.Inject

class OrderActivity : HiltOrderActivity() {

    @Inject
    lateinit var repository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
        Log.d(MainActivity.TAG, "orderActivity repository = $repository")
    }
}