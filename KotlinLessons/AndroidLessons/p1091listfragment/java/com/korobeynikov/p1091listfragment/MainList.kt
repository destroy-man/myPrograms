package com.korobeynikov.p1091listfragment

import android.app.ListFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.text.FieldPosition

class MainList:ListFragment() {

    val data=arrayOf("one","two","three","four")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter=ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,data)
        listAdapter=adapter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment,null)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        Toast.makeText(activity,"position = "+position,Toast.LENGTH_SHORT).show()
    }
}