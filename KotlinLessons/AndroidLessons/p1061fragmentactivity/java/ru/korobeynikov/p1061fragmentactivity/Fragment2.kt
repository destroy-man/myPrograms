package ru.korobeynikov.p1061fragmentactivity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p1061fragmentactivity.databinding.Fragment2Binding

class Fragment2 : Fragment() {

    interface OnSomeEventListener {
        fun someEvent(s: String)
    }

    private lateinit var someEventListener: OnSomeEventListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            someEventListener = activity as OnSomeEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${activity.toString()} must implement onSomeEventListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<Fragment2Binding>(inflater, R.layout.fragment_2, container, false)
        binding.view = this
        return binding.root
    }

    fun clickButton() {
        someEventListener.someEvent("Test text to Fragment1")
    }
}