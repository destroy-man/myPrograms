package ru.korobeynikov.p28navigationglobalactiondeeplink

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p28navigationglobalactiondeeplink.databinding.Fragment4Binding

class Fragment4 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val binding = DataBindingUtil.setContentView<Fragment4Binding>(it, R.layout.fragment4)
            binding.view = it as MainActivity
        }
    }
}