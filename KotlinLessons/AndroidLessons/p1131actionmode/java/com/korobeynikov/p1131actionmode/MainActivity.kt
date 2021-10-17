package com.korobeynikov.p1131actionmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var actionMode:ActionMode? = null
    val LOG_TAG="myLogs"
    val data=arrayOf("one","two","three","four","five")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,data)
        lvActionMode.adapter=adapter
        lvActionMode.choiceMode=ListView.CHOICE_MODE_MULTIPLE_MODAL
        lvActionMode.setMultiChoiceModeListener(object:AbsListView.MultiChoiceModeListener{

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.context,menu)
                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, p1: MenuItem?): Boolean {
                mode?.finish()
                return false
            }

            override fun onDestroyActionMode(p0: ActionMode?) {}

            override fun onItemCheckedStateChanged(p0: ActionMode?, position: Int, p2: Long, checked: Boolean) {
                Log.d(LOG_TAG,"position = "+position+", checked = "+checked)
            }
        })
    }
}