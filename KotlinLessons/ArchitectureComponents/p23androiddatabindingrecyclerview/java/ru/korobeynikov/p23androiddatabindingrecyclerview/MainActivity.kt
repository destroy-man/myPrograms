package ru.korobeynikov.p23androiddatabindingrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p23androiddatabindingrecyclerview.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listEmployees = LinkedList<Employee>()
        for (i in 1..100)
            listEmployees.add(Employee(i.toLong(), "Employee $i", "London", 10000 * i))
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        val employeeAdapter = EmployeeAdapter()
        employeeAdapter.setData(listEmployees)
        recyclerView.adapter = employeeAdapter
    }
}