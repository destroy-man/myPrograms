package ru.korobeynikov.p1061fragmentactivity

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class Fragment1:Fragment(){

    val LOG_TAG="myLogs"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater?.inflate(R.layout.fragment1,null)
        val button=v?.findViewById<Button>(R.id.button)
        button?.setOnClickListener{
            activity.btnFind.text="Access from Fragment1"
        }
        return v
    }
}