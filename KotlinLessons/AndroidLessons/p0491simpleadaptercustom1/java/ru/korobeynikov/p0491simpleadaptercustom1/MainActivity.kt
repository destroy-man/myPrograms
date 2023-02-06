package ru.korobeynikov.p0491simpleadaptercustom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0491simpleadaptercustom1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val values = intArrayOf(8, 4, -3, 2, -5, 0, 3, -6, 1, -1)
        val days = Array<String?>(values.size) { null }
        for (i in days.indices)
            days[i] = "Day ${i + 1}"
        val rvSimple = binding.rvSimple
        val layoutManager = LinearLayoutManager(this)
        rvSimple.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvSimple.context, layoutManager.orientation)
        rvSimple.addItemDecoration(dividerItemDecoration)
        rvSimple.adapter = ChartAdapter(values, days)
    }
}