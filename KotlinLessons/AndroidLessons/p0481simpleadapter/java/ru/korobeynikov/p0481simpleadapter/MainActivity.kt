package ru.korobeynikov.p0481simpleadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0481simpleadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val texts = arrayOf("sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5")
        val checked = booleanArrayOf(true, false, false, true, false)
        val rvSimple = binding.rvSimple
        val layoutManager = LinearLayoutManager(this)
        rvSimple.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvSimple.context, layoutManager.orientation)
        rvSimple.addItemDecoration(dividerItemDecoration)
        rvSimple.adapter = TextAdapter(texts, checked)
    }
}