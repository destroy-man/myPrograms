package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment3Binding

class Fragment3 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<Fragment3Binding>(requireActivity(), R.layout.fragment3)
        binding.view = activity as MainActivity
    }
}