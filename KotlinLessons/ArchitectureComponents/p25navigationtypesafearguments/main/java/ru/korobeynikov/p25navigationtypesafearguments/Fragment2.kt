package ru.korobeynikov.p25navigationtypesafearguments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p25navigationtypesafearguments.databinding.Fragment2Binding

class Fragment2 : Fragment() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val binding = DataBindingUtil.setContentView<Fragment2Binding>(it, R.layout.fragment2)
            binding.view = it as MainActivity
        }
        val fragment2Args = arguments?.let { Fragment2Args.fromBundle(it) }
        val arg3Value = fragment2Args?.arg3
        val arg4Value = fragment2Args?.arg4
        val arg5Value = fragment2Args?.arg5
        Log.d(logTag, "$arg3Value, $arg4Value, $arg5Value")
    }
}