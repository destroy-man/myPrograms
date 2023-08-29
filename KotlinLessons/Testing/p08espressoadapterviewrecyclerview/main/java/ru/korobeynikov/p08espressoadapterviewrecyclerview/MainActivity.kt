package ru.korobeynikov.p08espressoadapterviewrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p08espressoadapterviewrecyclerview.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val recyclerView = binding.recyclerView
        val data = LinkedList<Item>()
        for (i in 1 until 100)
            data.add(Item(i.toLong(), "Item $i", i / 10))
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = MyAdapter(this, data)
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this,
            object : RecyclerTouchListener.ClickListener {
                override fun onClick(view: View, position: Int) {
                    binding.textView.text = (recyclerView.adapter as MyAdapter).getItem(position).name
                }
            }))
    }
}