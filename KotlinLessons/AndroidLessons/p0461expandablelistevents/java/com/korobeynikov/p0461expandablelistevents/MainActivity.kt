package com.korobeynikov.p0461expandablelistevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var ah:AdapterHelper
    lateinit var adapter:SimpleExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ah=AdapterHelper(this)
        adapter=ah.getAdapter()
        elvMain.setAdapter(adapter)
        elvMain.setOnChildClickListener(object:ExpandableListView.OnChildClickListener{
            override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                Log.d(LOG_TAG,"onChildClick groupPosition = "+groupPosition+" childPosition = "
                        +childPosition+" id = "+id)
                tvInfo.text=ah.getGroupChildText(groupPosition,childPosition)
                return false
            }
        })
        elvMain.setOnGroupClickListener(object:ExpandableListView.OnGroupClickListener{
            override fun onGroupClick(parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long): Boolean {
                Log.d(LOG_TAG,"onGroupClick groupPosition = "+groupPosition+" id = "+id)
                if(groupPosition==1)return true
                return false
            }
        })
        elvMain.setOnGroupCollapseListener{
            Log.d(LOG_TAG,"onGroupCollapse groupPosition = "+it)
            tvInfo.text="Свернули "+ah.getGroupText(it)
        }
        elvMain.setOnGroupExpandListener{
            Log.d(LOG_TAG,"onGroupExpand groupPosition = "+it)
            tvInfo.text="Развернули "+ah.getGroupText(it)
        }
        elvMain.expandGroup(2)
    }
}