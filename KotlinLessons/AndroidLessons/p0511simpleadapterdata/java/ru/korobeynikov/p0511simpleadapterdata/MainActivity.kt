package ru.korobeynikov.p0511simpleadapterdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0511simpleadapterdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var nameAdapter: NameAdapter
    private lateinit var data: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        data = arrayListOf()
        for (i in 1 until 5)
            data.add("sometext $i")
        val rvSimple = binding.rvSimple
        val layoutManager = LinearLayoutManager(this)
        rvSimple.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvSimple.context, layoutManager.orientation)
        rvSimple.addItemDecoration(dividerItemDecoration)
        nameAdapter = NameAdapter(data)
        rvSimple.adapter = nameAdapter
        registerForContextMenu(rvSimple)
    }

    fun buttonClick() {
        data.add("sometext ${data.size + 1}")
        nameAdapter.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        data.removeAt(item.groupId)
        nameAdapter.notifyDataSetChanged()
        return true
    }
}