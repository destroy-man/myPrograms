package ru.korobeynikov.p1061fragmentactivity

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Fragment2:Fragment(){

    interface onSomeEventListener{
        fun someEvent(s:String)
    }

    lateinit var someEventListener:onSomeEventListener

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try{
            someEventListener=activity as onSomeEventListener
        }
        catch(e:ClassCastException){
            ClassCastException("${activity.toString()} must implement onSomeEventListener")
        }
    }

    val LOG_TAG="myLogs"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater?.inflate(R.layout.fragment2,null)
        val button=v?.findViewById<Button>(R.id.button)
        button?.setOnClickListener{
            someEventListener.someEvent("Test text to Fragment1")
        }
        return v
    }
}