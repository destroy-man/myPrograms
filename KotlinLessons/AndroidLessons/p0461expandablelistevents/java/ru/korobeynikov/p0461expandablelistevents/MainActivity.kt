package ru.korobeynikov.p0461expandablelistevents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleExpandableListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var ah:AdapterHelper
    lateinit var adapter:SimpleExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ah= AdapterHelper(this)
        adapter=ah.getAdapter()
        elvMain.setAdapter(adapter)
        elvMain.setOnChildClickListener { p0, p1, groupPosition, childPosition, id ->
            Log.d(LOG_TAG, "onChildClick groupPosition = "+groupPosition+" childPosition = "+childPosition+" id = "+id)
            tvInfo.text=ah.getGroupChildText(groupPosition, childPosition)
            false
        }
        elvMain.setOnGroupClickListener { expandableListView, view, groupPosition, id ->
            Log.d(LOG_TAG,"onGroupClick groupPosition = "+groupPosition+" id = "+id)
            false
        }
        elvMain.setOnGroupCollapseListener { groupPosition ->
            Log.d(LOG_TAG,"onGroupCollapse groupPosition = "+groupPosition)
            tvInfo.text="Свернули "+ah.getGroupText(groupPosition)
        }
        elvMain.setOnGroupExpandListener { groupPosition ->
            Log.d(LOG_TAG,"onGroupExpand groupPosition = "+groupPosition)
            tvInfo.text="Развернули "+ah.getGroupText(groupPosition)
            if(groupPosition==1)
                elvMain.collapseGroup(groupPosition)
        }
        elvMain.expandGroup(2)
    }
}