package com.korobeynikov.p1061fragmentactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment1.view.*

class Fragment1:Fragment() {

    val LOG_TAG="myLogs"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment1,null)
        v.button.setOnClickListener {
            activity?.btnFind?.text="Access from Fragment1"
        }
        return v
    }
}