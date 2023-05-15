package ru.korobeynikov.p1121dynamicactionbar

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1121dynamicactionbar.databinding.Fragment1Binding

class Fragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment1Binding>(inflater, R.layout.fragment_1, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment1, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}