package ru.korobeynikov.p1141supportlibrary

import android.os.Bundle
import android.support.v4.app.FragmentActivity

class MainActivity:FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myFrag=MyFragment()
        supportFragmentManager.beginTransaction().replace(R.id.cont,myFrag).commit()
    }
}