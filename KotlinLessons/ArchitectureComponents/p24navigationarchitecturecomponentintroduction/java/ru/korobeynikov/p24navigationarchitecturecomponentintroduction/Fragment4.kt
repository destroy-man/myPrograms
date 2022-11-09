package ru.korobeynikov.p24navigationarchitecturecomponentintroduction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p24navigationarchitecturecomponentintroduction.databinding.Fragment4Binding

class Fragment4 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<Fragment4Binding>(requireActivity(), R.layout.fragment4)
        binding.view = activity as SecondActivity
    }
}