package com.korobeynikov.p0711preferencessimple

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
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

    override fun onResume() {
        val notif=sp.getBoolean("notif",false)
        val address=sp.getString("address","")
        val text="Notifications are "+(
                if(notif)"enabled, address = "+address
                else "disabled"
        )
        tvInfo.text=text
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean{
        val mi=menu.add(0,1,0,"Preferences")
        mi.intent=Intent(this,PrefActivity::class.java)
        return super.onCreateOptionsMenu(menu)
    }
}