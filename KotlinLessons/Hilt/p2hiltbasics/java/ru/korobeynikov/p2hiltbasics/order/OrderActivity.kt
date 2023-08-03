package ru.korobeynikov.p2hiltbasics.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p2hiltbasics.other.MainActivity
import ru.korobeynikov.p2hiltbasics.R
import ru.korobeynikov.p2hiltbasics.databinding.ActivityOrderBinding
import javax.inject.Inject

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityOrderBinding>(this, R.layout.activity_order)
        Log.d(MainActivity.TAG, "order repository = $repository")
    }
}