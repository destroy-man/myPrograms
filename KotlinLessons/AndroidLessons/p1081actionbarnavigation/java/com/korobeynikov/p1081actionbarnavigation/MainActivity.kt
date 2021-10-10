package com.korobeynikov.p1081actionbarnavigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), android.app.ActionBar.OnNavigationListener {

    val data=arrayOf("one","two","three")
    val LOG_TAG="myLogs"

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBar?.navigationMode = ActionBar.NAVIGATION_MODE_LIST
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        actionBar?.setListNavigationCallbacks(adapter,this)
    }

    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
        Log.d(LOG_TAG,"selected: position = "+itemPosition+", id = "+itemId+", "+data[itemPosition])
        return false
    }
}