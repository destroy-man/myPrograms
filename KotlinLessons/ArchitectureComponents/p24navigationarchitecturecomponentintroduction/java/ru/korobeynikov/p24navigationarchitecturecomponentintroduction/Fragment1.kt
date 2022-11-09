package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<Fragment1Binding>(requireActivity(), R.layout.fragment1)
        binding.view = activity as MainActivity
    }
}