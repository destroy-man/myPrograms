package com.korobeynikov.p0051builder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val someObject=SomeObject()
        val appComponent=DaggerAppComponent.builder().setMyInstanceOfSomeObject(someObject).letsBuildThisComponent()
    }
}