package ru.korobeynikov.p27navigationui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import ru.korobeynikov.p27navigationui.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let { fragmentActivity ->
            val binding = DataBindingUtil.setContentView<Fragment1Binding>(fragmentActivity,
                R.layout.fragment1)
            binding.view = fragmentActivity as MainActivity
            val button = binding.bNext
            button.setOnClickListener {
                Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment)
                    .navigate(R.id.fragment2)
            }
        }
    }
}