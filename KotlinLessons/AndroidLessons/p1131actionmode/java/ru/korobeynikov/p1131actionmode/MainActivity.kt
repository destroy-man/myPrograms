package ru.korobeynikov.p1131actionmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p1131actionmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val data = arrayOf("one", "two", "three", "four", "five")
    val logTag = "myLogs"
    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val names = arrayListOf<String>()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val rvList = binding.rvList
        val layoutManager = LinearLayoutManager(this)
        with(rvList) {
            this.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
            adapter = ListAdapter(data, clickButton)
            addOnItemTouchListener(RecyclerTouchListener(this@MainActivity,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val name = (rvList.adapter as ListAdapter).getItem(position)
                        if (names.contains(name))
                            names.remove(name)
                        else
                            names.add(name)
                        val listNames = "$names".replace("[", "").replace("]", "")
                        if (listNames.isNotEmpty())
                            Toast.makeText(this@MainActivity, listNames, Toast.LENGTH_SHORT).show()
                    }
                }))
        }
    }

    private val clickButton: (view: View) -> Boolean = {
        if (actionMode == null)
            actionMode = startActionMode(callback)
        else
            actionMode?.finish()
        true
    }

    private val callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.context, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            Log.d(logTag, "item ${item?.title}")
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            Log.d(logTag, "destroy")
            actionMode = null
        }
    }
}