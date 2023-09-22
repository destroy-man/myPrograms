package ru.korobeynikov.p25navigationtypesafearguments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p25navigationtypesafearguments.databinding.Fragment2Binding

class Fragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment2Binding>(inflater, R.layout.fragment2, container, false)
        binding.view = activity as MainActivity
        arguments?.let {
            val arg1Value = it.getString("arg1")
            val arg2Value = it.getInt("arg2")
            val arg3Value = it.getString("arg3")
            val arg4Value = resources.getString(it.getInt("arg4"))
            val arg5Value = resources.getString(it.getInt("arg5"))
            Log.d("myLogs", "arg1 = $arg1Value, arg2 = $arg2Value, arg3 = $arg3Value, " +
                    "arg4 = $arg4Value, arg5 = $arg5Value")
        }
        return binding.root
    }
}