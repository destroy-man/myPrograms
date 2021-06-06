package com.korobeynikov.p0551headerfooter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.HeaderViewListAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.footer.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.tvText
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val data=arrayOf("one","two","three","four","five")
    lateinit var adapter: ArrayAdapter<String>
    lateinit var header1:View
    lateinit var header2:View
    lateinit var footer1:View
    lateinit var footer2:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        header1=createHeader("header 1")
        header2=createHeader("header 2")
        footer1=createFooter("footer 1")
        footer2=createFooter("footer 2")
        fillList()
    }

    fun fillList(){
        lvMain.addHeaderView(header1)
        lvMain.addHeaderView(header2,"some text for header 2",false)
        lvMain.addFooterView(footer1)
        lvMain.addFooterView(footer2,"some text for footer 2",false)
        lvMain.adapter=adapter
    }

    fun onclick(v:View){
        var obj:Object?=null
        val hvlAdapter=lvMain.adapter as HeaderViewListAdapter
        obj=hvlAdapter.getItem(1) as Object
        Log.d(LOG_TAG,"hvlAdapter.getItem(1) = "+obj.toString())
        obj=hvlAdapter.getItem(4) as Object
        Log.d(LOG_TAG,"hvlAdapter.getItem(4) = "+obj.toString())
        val alAdapter=hvlAdapter.wrappedAdapter
        obj=alAdapter.getItem(1) as Object
        Log.d(LOG_TAG,"alAdapter.getItem(1) = "+obj.toString())
        obj=alAdapter.getItem(4) as Object
        Log.d(LOG_TAG,"alAdapter.getItem(4) = "+obj.toString())
    }

    fun createHeader(text:String):View{
        val v=layoutInflater.inflate(R.layout.header,null)
        v.findViewById<TextView>(R.id.tvText).text=text
        return v
    }

    fun createFooter(text:String):View{
        val v=layoutInflater.inflate(R.layout.footer,null)
        v.findViewById<TextView>(R.id.tvText).text=text
        return v
    }
}