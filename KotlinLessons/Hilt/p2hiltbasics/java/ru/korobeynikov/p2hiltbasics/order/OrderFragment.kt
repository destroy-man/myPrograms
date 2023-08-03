package ru.korobeynikov.p2hiltbasics.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p2hiltbasics.R
import ru.korobeynikov.p2hiltbasics.databinding.FragmentOrderBinding
import ru.korobeynikov.p2hiltbasics.other.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment() {

    @Inject
    lateinit var repository: OrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(MainActivity.TAG, "fragment repository = $repository")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentOrderBinding>(inflater, R.layout.fragment_order, container, false)
        return binding.root
    }
}