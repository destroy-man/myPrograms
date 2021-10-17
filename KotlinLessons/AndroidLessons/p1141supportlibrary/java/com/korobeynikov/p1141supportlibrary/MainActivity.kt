package com.korobeynikov.p1141supportlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myFrag=MyFragment()
        supportFragmentManager.beginTransaction().replace(R.id.cont,myFrag).commit()
    }
}