package ru.korobeynikov.p24navigationarchitecturecomponentintroduction.main_graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.R
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment1Binding>(inflater, R.layout.fragment1, container, false)
        binding.view = activity as MainActivity
        return binding.root
    }
}