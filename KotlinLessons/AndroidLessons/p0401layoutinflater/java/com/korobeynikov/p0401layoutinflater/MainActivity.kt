package com.korobeynikov.p0401layoutinflater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ltInflater=layoutInflater
        val view1=ltInflater.inflate(R.layout.text,linLayout,true)
        val lp1=view1.layoutParams
        Log.d(LOG_TAG,"Class of view1: "+view1::class.toString())
        Log.d(LOG_TAG,"Class of layoutParams of view1: "+lp1::class.toString())
        val view2=ltInflater.inflate(R.layout.text,relLayout,true)
        val lp2=view2.layoutParams
        Log.d(LOG_TAG,"Class of view2: "+view2::class.toString())
        Log.d(LOG_TAG,"Class of layoutParams of view2: "+lp2::class.toString())
    }
}