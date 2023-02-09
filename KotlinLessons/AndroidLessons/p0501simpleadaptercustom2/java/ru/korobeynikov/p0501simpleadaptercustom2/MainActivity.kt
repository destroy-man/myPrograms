package ru.korobeynikov.p0501simpleadaptercustom2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0501simpleadaptercustom2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val load = intArrayOf(41, 48, 22, 35, 30, 67, 51, 88)
        val days = Array(load.size) { "" }
        for (i in days.indices)
            days[i] = "Day ${i + 1}. Load: ${load[i]}%"
        val rvSimple = binding.rvSimple
        val layoutManager = LinearLayoutManager(this)
        rvSimple.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvSimple.context, layoutManager.orientation)
        rvSimple.addItemDecoration(dividerItemDecoration)
        rvSimple.adapter = ChartAdapter(load, days)
    }
}