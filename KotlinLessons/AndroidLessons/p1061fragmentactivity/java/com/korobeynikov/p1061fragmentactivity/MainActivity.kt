package com.korobeynikov.p1061fragmentactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment1.*
import kotlinx.android.synthetic.main.fragment2.view.*

class MainActivity : AppCompatActivity(),Fragment2.onSomeEventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frag2=Fragment2()
        val ft=supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment2,frag2)
        ft.commit()
    }

    fun onClick(v:View){
        fragment1.textView.text="Access to Fragment 1 from Activity"
        fragment2.textView.text="Access to Fragment 2 from Activity"
    }

    override fun someEvent(s: String) {
        fragment1.textView.text="Text from Fragment 2: "+s
    }
}