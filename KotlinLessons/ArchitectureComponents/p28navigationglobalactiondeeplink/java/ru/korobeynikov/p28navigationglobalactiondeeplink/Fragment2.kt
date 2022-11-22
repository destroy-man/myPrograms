package ru.korobeynikov.p28navigationglobalactiondeeplink

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p28navigationglobalactiondeeplink.databinding.Fragment2Binding

class Fragment2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val binding = DataBindingUtil.setContentView<Fragment2Binding>(it, R.layout.fragment2)
            binding.view = it as MainActivity
        }
    }
}