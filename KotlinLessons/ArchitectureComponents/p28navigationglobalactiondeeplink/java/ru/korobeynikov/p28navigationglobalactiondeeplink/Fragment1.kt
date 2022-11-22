package ru.korobeynikov.p28navigationglobalactiondeeplink

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p28navigationglobalactiondeeplink.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val binding = DataBindingUtil.setContentView<Fragment1Binding>(it, R.layout.fragment1)
            binding.view = it as MainActivity
        }
    }
}