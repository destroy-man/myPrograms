package ru.korobeynikov.p0731preferencesenable

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi=menu?.add(0,1,0,"Preferences")
        mi?.intent=Intent(this,PrefActivity::class.java)
        return super.onCreateOptionsMenu(menu)
    }
}