package ru.korobeynikov.p0691parcelable

import android.content.Intent
import android.support.v7.app.AppCompatActivity
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
        intent.putExtra(MyObject::class.java.canonicalName,myObj)
        Log.d(LOG_TAG,"startActivity")
        startActivity(intent)
    }
}