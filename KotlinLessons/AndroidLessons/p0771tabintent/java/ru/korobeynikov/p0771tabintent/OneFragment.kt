package ru.korobeynikov.p0771tabintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p0771tabintent.databinding.FragmentOneBinding

class OneFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<FragmentOneBinding>(inflater, R.layout.fragment_one, null, false)
        return binding.root
    }
}