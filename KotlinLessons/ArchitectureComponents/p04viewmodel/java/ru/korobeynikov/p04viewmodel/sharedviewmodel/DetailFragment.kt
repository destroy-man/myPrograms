package ru.korobeynikov.p04viewmodel.sharedviewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p04viewmodel.R
import ru.korobeynikov.p04viewmodel.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentDetailBinding>(inflater, R.layout.fragment_detail, container, false)
        activity?.let {
            val model = ViewModelProvider(it)[SharedViewModel::class.java]
            model.getSelected().observe(viewLifecycleOwner) { item ->
                binding.textView.text = item.name
            }
        }
        return binding.root
    }
}