package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment2Binding

class Fragment2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<Fragment2Binding>(requireActivity(), R.layout.fragment2)
        binding.view = activity as MainActivity
    }
}