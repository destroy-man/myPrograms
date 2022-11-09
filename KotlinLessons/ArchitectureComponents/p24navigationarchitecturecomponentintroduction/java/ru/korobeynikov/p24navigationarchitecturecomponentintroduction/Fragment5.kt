package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment5Binding

class Fragment5 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<Fragment5Binding>(requireActivity(), R.layout.fragment5)
        binding.view = activity as SecondActivity
    }
}