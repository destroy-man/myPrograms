package ru.korobeynikov.p0721preferencessimple2

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp=PreferenceManager.getDefaultSharedPreferences(this)
    }

    protected override fun onResume() {
        val listValue=sp.getString("list","не выбрано")
        tvInfo.text="Значение списка - $listValue"
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi=menu?.add(0,1,0,"Preferences")
        mi?.intent=Intent(this,PrefActivity::class.java)
        return super.onCreateOptionsMenu(menu)
    }
}