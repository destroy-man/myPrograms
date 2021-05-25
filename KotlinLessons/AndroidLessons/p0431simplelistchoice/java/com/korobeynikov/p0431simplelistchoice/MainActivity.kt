package com.korobeynikov.p0431simplelistchoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    val LOG_TAG="myLogs"
    lateinit var names:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvMain.choiceMode=ListView.CHOICE_MODE_MULTIPLE
        val adapter=ArrayAdapter.createFromResource(this,R.array.names,android.R.layout.simple_list_item_multiple_choice)
        lvMain.adapter=adapter
        btnChecked.setOnClickListener(this)
        names=resources.getStringArray(R.array.names)
    }

    override fun onClick(v: View?) {
        Log.d(LOG_TAG,"checked: ")
        val sbArray=lvMain.checkedItemPositions
        for(i in 0..sbArray.size()-1){
            val key=sbArray.keyAt(i)
            if(sbArray.get(key))
                Log.d(LOG_TAG,names[key])
        }
    }
}