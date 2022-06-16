package ru.korobeynikov.p1081actionbarnavigation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity(),ActionBar.OnNavigationListener {

    val data=arrayOf("one","two","three")
    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bar=supportActionBar
        bar?.navigationMode=ActionBar.NAVIGATION_MODE_LIST
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bar?.setListNavigationCallbacks(adapter,this)
    }

    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
        Log.d(LOG_TAG,"selected: position = $itemPosition, id = $itemId, ${data[itemPosition]}")
        return false
    }
}