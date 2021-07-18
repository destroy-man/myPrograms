package com.korobeynikov.p0691parcelable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second)

        Log.d(LOG_TAG,"getParcelableExtra")
        val myObj=intent.getParcelableExtra(MyObject.javaClass.canonicalName) as MyObject
        Log.d(LOG_TAG,"myObj: "+myObj.s+", "+myObj.i)
    }
}