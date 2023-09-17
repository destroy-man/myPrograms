package ru.korobeynikov.p14paginglibrarybasics.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.korobeynikov.p14paginglibrarybasics.R
import ru.korobeynikov.p14paginglibrarybasics.databinding.ActivityMainBinding
import ru.korobeynikov.p14paginglibrarybasics.employee.EmployeeAdapter
import ru.korobeynikov.p14paginglibrarybasics.employee.EmployeeDiffUtilCallback
import ru.korobeynikov.p14paginglibrarybasics.employee.EmployeeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val recyclerView = binding.recyclerView
        val viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        val employeeAdapter = EmployeeAdapter(EmployeeDiffUtilCallback())
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = employeeAdapter
        lifecycleScope.launch {
            employeeAdapter.loadStateFlow.collect {
                binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                binding.appendProgress.isVisible = it.source.append is LoadState.Loading
            }
        }
        lifecycleScope.launch {
            viewModel.employees.collectLatest {
                employeeAdapter.submitData(it)
            }
        }
    }
}