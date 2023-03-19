package ru.korobeynikov.p0771tabintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p0771tabintent.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<FragmentTwoBinding>(inflater, R.layout.fragment_two, null, false)
        return binding.root
    }
}