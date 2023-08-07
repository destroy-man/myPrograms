package ru.korobeynikov.p6hiltcustomcomponent.other

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p6hiltcustomcomponent.R
import ru.korobeynikov.p6hiltcustomcomponent.databinding.FragmentSomeBinding
import ru.korobeynikov.p6hiltcustomcomponent.di.MyEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SomeFragment : Fragment() {

    @Inject
    lateinit var myComponentManager: MyComponentManager

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myComponent = myComponentManager.get()
        myComponent?.let {
            val repository = EntryPoints.get(it, MyEntryPoint::class.java).getMyRepository()
            Log.d(logTag, "repository = $repository")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<FragmentSomeBinding>(inflater, R.layout.fragment_some, container, false)
        return binding.root
    }
}