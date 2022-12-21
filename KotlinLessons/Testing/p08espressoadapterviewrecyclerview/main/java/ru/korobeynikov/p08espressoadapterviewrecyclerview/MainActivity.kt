package ru.korobeynikov.p08espressoadapterviewrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p08espressoadapterviewrecyclerview.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val data = LinkedList<Item>()
        for (i in 1 until 100)
            data.add(Item(i.toLong(), "Item $i", i / 10))
        val adapterData = ArrayList<Map<String, Any>>(data.size)
        for (i in 0 until data.size) {
            val item = HashMap<String, Any>()
            item["nameItem"] = data[i].name
            item["tenItem"] = data[i].ten
            adapterData.add(item)
        }
        val from = arrayOf("nameItem", "tenItem")
        val to = intArrayOf(R.id.textViewName, R.id.textViewTen)
        val adapter = SimpleAdapter(this, adapterData, R.layout.item, from, to)
        binding.list.adapter = adapter
        binding.list.setOnItemClickListener { adapterView, view, i, l ->
            binding.textView.text = view.findViewById<TextView>(R.id.textViewName).text
        }
    }
}