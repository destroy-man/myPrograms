package com.korobeynikov.p0691parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        val myObj=MyObject("text",1)
        val intent=Intent(this,SecondActivity::class.java)
        intent.putExtra(MyObject.javaClass.canonicalName,myObj)
        Log.d(LOG_TAG,"startActivity")
        startActivity(intent)
    }
}