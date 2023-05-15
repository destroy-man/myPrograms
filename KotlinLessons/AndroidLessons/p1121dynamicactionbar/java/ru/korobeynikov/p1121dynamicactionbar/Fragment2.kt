package ru.korobeynikov.p1121dynamicactionbar

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1121dynamicactionbar.databinding.Fragment2Binding

class Fragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment2Binding>(inflater, R.layout.fragment_2, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment2, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}