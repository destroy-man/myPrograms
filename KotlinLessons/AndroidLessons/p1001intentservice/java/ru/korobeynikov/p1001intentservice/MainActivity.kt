package ru.korobeynikov.p1001intentservice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentMyService=Intent(this,MyService::class.java)

        startService(intentMyService.putExtra("time",3).putExtra("label","Call 1"))
        startService(intentMyService.putExtra("time",1).putExtra("label","Call 2"))
        startService(intentMyService.putExtra("time",4).putExtra("label","Call 3"))
    }
}