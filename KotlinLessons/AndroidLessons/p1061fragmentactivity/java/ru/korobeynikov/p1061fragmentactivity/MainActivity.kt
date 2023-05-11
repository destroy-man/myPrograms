package ru.korobeynikov.p1061fragmentactivity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1061fragmentactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Fragment2.OnSomeEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment2, Fragment2())
        ft.commit()
    }

    override fun someEvent(s: String) {
        val frag1 = supportFragmentManager.findFragmentById(R.id.fragment1)
        frag1?.view?.findViewById<TextView>(R.id.textView)?.text = getString(R.string.text_fragment2, s)
    }
}