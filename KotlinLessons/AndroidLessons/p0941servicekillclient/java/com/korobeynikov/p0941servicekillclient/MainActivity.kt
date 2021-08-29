package com.korobeynikov.p0941servicekillclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickStart(v:View){
        val serviceIntent=Intent("com.korobeynikov.p0942servicekillserver.MyService").putExtra("name","value")
        serviceIntent.setPackage("com.korobeynikov.p0942servicekillserver")
        startService(serviceIntent)
    }
}