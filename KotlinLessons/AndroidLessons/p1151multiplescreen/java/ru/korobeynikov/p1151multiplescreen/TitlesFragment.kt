package ru.korobeynikov.p1151multiplescreen

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

class TitlesFragment:ListFragment(){

    interface onItemClickListener{
        fun itemClick(position:Int)
    }

    lateinit var listener:onItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity!=null){
            val adapter=ArrayAdapter(activity!!,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.headers))
            listAdapter=adapter
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        listener=activity as onItemClickListener
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        listener.itemClick(position)
    }
}