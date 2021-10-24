package com.korobeynikov.p1151multiplescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.details.view.*

class DetailsFragment:Fragment() {

    companion object {
        fun newInstance(pos: Int): DetailsFragment {
            val details = DetailsFragment()
            val args = Bundle()
            args.putInt("position", pos)
            details.arguments = args
            return details
        }
    }

    fun getPosition(): Int? {
        return arguments?.getInt("position",0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.details,container,false)
        v.tvText.text=resources.getStringArray(R.array.content)[getPosition()!!]
        return v
    }
}