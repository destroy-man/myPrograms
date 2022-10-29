package ru.korobeynikov.p17pagingsource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diffUtilCallback = EmployeeDiffUtilCallback()
        val viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        val items = viewModel.items
        val adapter = EmployeeAdapter(diffUtilCallback)
        items.observe(this) { employees ->
            adapter.submitData(lifecycle, employees)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}