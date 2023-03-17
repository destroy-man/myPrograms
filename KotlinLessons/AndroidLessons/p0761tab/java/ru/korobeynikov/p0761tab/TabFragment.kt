package ru.korobeynikov.p0761tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p0761tab.databinding.TabLayoutBinding

class TabFragment : Fragment() {

    private lateinit var binding: TabLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_layout, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tabText.text = arguments?.get(Constants.ARG_OBJECT).toString()
    }
}