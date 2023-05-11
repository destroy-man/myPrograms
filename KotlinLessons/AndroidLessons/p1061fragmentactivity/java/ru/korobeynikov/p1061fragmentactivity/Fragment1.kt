package ru.korobeynikov.p1061fragmentactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p1061fragmentactivity.databinding.Fragment1Binding

class Fragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment1Binding>(inflater, R.layout.fragment_1, container, false)
        binding.view = this
        return binding.root
    }

    fun clickButton() {
        activity?.findViewById<Button>(R.id.btnFind)?.text = getText(R.string.access_fragment1)
    }
}