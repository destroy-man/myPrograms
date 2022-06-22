package ru.korobeynikov.p0941servicekillclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickStart(v:View){
        val serviceIntent=Intent("ru.korobeynikov.p0941servicekillclient.MyService")
            .putExtra("name","value")
        serviceIntent.setPackage("ru.korobeynikov.p0941servicekillclient")
        startService(serviceIntent)
    }
}