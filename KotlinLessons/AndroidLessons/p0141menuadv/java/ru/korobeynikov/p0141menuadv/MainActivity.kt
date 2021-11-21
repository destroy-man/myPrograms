package ru.korobeynikov.p0141menuadv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var tv:TextView
    lateinit var chb:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv=textView
        chb=chbExtMenu
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.setGroupVisible(R.id.group1,chb.isChecked)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sb=StringBuilder()
        sb.append("Item Menu")
        sb.append("\r\n groupId: "+item.groupId)
        sb.append("\r\n itemId: "+item.itemId)
        sb.append("\r\n order: "+item.order)
        sb.append("\r\n title: "+item.title)
        tv.text=sb.toString()
        return super.onOptionsItemSelected(item)
    }
}