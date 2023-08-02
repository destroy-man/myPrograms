package ru.korobeynikov.p1hiltunderthehood.other

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1hiltunderthehood.R
import ru.korobeynikov.p1hiltunderthehood.databinding.FragmentOrderBinding
import javax.inject.Inject


class OrderFragment : HiltOrderFragment() {

    @Inject
    lateinit var orderRepository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(MainActivity.TAG, "fragment orderRepository = $orderRepository")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentOrderBinding>(inflater, R.layout.fragment_order, container, false)
        return binding.root
    }
}