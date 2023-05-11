package ru.korobeynikov.p1051fragmentdynamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1051fragmentdynamic.databinding.Fragment2Binding

class Fragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment2Binding>(inflater, R.layout.fragment_2, container, false)
        return binding.root
    }
}