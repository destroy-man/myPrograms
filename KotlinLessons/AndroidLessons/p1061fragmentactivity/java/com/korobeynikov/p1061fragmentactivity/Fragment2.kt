package com.korobeynikov.p1061fragmentactivity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment2.view.*
import java.lang.ClassCastException

class Fragment2:Fragment() {

    interface onSomeEventListener{
        fun someEvent(s:String)
    }

    lateinit var someEventListener: onSomeEventListener

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try{
            someEventListener=activity as onSomeEventListener
        }
        catch(e:ClassCastException){
            throw ClassCastException(activity.toString()+" must implement onSomeEventListener")
        }
    }

    val LOG_TAG="myLogs"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment2,null)
        v.button.setOnClickListener {
            someEventListener.someEvent("Test text to Fragment1")
        }
        return v
    }
}