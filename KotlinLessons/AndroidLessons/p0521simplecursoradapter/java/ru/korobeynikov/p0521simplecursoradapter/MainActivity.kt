package ru.korobeynikov.p0521simplecursoradapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0521simplecursoradapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var nameAdapter: NameAdapter
    private lateinit var data: ArrayList<String>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
        data = arrayListOf()
        for (i in 1 until 5)
            data.add("sometext $i")
        val rvData = binding.rvData
        val layoutManager = LinearLayoutManager(this)
        rvData.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvData.context, layoutManager.orientation)
        rvData.addItemDecoration(dividerItemDecoration)
        nameAdapter = NameAdapter(data)
        rvData.adapter = nameAdapter
        registerForContextMenu(rvData)
    }

    private fun checkFillFields(position: String, text: String): Boolean {
        if (position.isEmpty()) {
            Toast.makeText(this, "Необходимо указать позицию записи!", Toast.LENGTH_SHORT).show()
            return false
        } else if (text.isEmpty()) {
            Toast.makeText(this, "Необходимо указать текст записи!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun addRecord() {
        val position = binding.textPositionRecord.text.toString()
        val text = binding.textRecord.text.toString()
        if (checkFillFields(position, text)) {
            data.add(position.toInt(), text)
            nameAdapter.notifyDataSetChanged()
        }
    }

    fun changeRecord() {
        val position = binding.textPositionRecord.text.toString()
        val text = binding.textRecord.text.toString()
        if (checkFillFields(position, text)) {
            data[position.toInt()] = text
            nameAdapter.notifyDataSetChanged()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        data.removeAt(item.groupId)
        nameAdapter.notifyDataSetChanged()
        return true
    }
}