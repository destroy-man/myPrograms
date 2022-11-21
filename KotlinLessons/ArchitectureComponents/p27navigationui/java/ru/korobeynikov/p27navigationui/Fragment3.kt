package ru.korobeynikov.p27navigationui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p27navigationui.databinding.Fragment3Binding

class Fragment3 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val binding = DataBindingUtil.setContentView<Fragment3Binding>(it, R.layout.fragment3)
            binding.view = it as MainActivity
        }
    }
}