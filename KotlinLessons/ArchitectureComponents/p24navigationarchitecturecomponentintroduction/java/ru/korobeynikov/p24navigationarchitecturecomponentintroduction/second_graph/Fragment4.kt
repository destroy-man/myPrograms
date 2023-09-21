package ru.korobeynikov.p24navigationarchitecturecomponentintroduction.second_graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.R
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment4Binding

class Fragment4 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment4Binding>(inflater, R.layout.fragment4, container, false)
        binding.view = activity as SecondActivity
        return binding.root
    }
}