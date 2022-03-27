package ru.korobeynikov.p0691parcelable

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second)

        Log.d(LOG_TAG,"getParcelableExtra")
        val myObj=intent.getParcelableExtra<MyObject>(MyObject::class.java.canonicalName)
        Log.d(LOG_TAG,"myObj: ${myObj?.s}, ${myObj?.i}")
    }
}