package ru.korobeynikov.p0921servicesimple

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickStart(v:View){
        startService(Intent(this,MyService::class.java))
    }

    fun onClickStop(v:View){
        stopService(Intent(this,MyService::class.java))
    }
}