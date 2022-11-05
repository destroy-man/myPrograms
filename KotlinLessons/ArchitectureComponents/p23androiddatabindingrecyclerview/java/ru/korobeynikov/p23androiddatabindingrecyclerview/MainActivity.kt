package ru.korobeynikov.p23androiddatabindingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p23androiddatabindingrecyclerview.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listEmployees = LinkedList<Employee>()
        listEmployees.add(Employee(1, "John Smith", "London", 10000))
        listEmployees.add(Employee(2, "Ivan Ivanov", "Moscow", 20000))
        val adapter = EmployeeAdapter()
        adapter.setData(listEmployees)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}