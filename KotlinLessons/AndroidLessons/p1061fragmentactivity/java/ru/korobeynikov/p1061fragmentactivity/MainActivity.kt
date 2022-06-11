package ru.korobeynikov.p1061fragmentactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment1.textView


class MainActivity : AppCompatActivity(),Fragment2.onSomeEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frag2=Fragment2()
        val ft=fragmentManager.beginTransaction()
        ft.add(R.id.fragment2,frag2)
        ft.commit()
    }

    fun onClick(v:View){
        val frag1=fragmentManager.findFragmentById(R.id.fragment1)
        frag1.textView.text="Access to Fragment 1 from Activity"
        val frag2=fragmentManager.findFragmentById(R.id.fragment2)
        frag2.textView.text="Access to Fragment 2 from Activity"
    }

    override fun someEvent(s: String) {
        textView.text="Text from Fragment 2:$s"
    }
}