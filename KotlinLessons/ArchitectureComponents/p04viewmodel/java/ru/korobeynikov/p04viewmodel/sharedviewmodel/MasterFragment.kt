package ru.korobeynikov.p04viewmodel.sharedviewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p04viewmodel.R
import ru.korobeynikov.p04viewmodel.databinding.FragmentMasterBinding

class MasterFragment : Fragment() {

    private lateinit var model: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentMasterBinding>(inflater, R.layout.fragment_master, container, false)
        binding.view = this
        activity?.let {
            model = ViewModelProvider(it)[SharedViewModel::class.java]
        }
        return binding.root
    }

    fun clickButton() = model.select(Item("master"))
}