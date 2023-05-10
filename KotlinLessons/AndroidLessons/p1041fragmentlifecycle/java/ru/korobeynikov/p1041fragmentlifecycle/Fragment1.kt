package ru.korobeynikov.p1041fragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1041fragmentlifecycle.databinding.Fragment1Binding

class Fragment1 : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(MainActivity.LOG_TAG, "Fragment1 onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(MainActivity.LOG_TAG, "Fragment1 onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d(MainActivity.LOG_TAG, "Fragment1 onCreateView")
        val binding =
            DataBindingUtil.inflate<Fragment1Binding>(inflater, R.layout.fragment_1, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(MainActivity.LOG_TAG, "Fragment1 onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(MainActivity.LOG_TAG, "Fragment1 onDetach")
    }
}