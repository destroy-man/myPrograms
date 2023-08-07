package ru.korobeynikov.p6hiltcustomcomponent.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p6hiltcustomcomponent.R
import ru.korobeynikov.p6hiltcustomcomponent.databinding.FragmentRootBinding
import javax.inject.Inject

@AndroidEntryPoint
class RootFragment : Fragment() {

    @Inject
    lateinit var myComponentManager: MyComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            myComponentManager.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isDestroyed == true)
            myComponentManager.destroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<FragmentRootBinding>(inflater, R.layout.fragment_root, container, false)
        return binding.root
    }
}