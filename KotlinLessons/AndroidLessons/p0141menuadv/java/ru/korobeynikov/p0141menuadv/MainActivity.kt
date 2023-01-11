package ru.korobeynikov.p0141menuadv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0141menuadv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private lateinit var chb: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tv = binding.textView
        chb = binding.chbExtMenu
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.setGroupVisible(R.id.group1, chb.isChecked)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sb = StringBuilder("Item Menu" +
                "\r\n groupId: ${item.groupId}" +
                "\r\n itemId: ${item.itemId}" +
                "\r\n order: ${item.order}" +
                "\r\n title: ${item.title}")
        tv.text = sb
        return super.onOptionsItemSelected(item)
    }
}