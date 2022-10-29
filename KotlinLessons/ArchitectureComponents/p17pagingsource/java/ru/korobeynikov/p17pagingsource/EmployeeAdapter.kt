package ru.korobeynikov.p17pagingsource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

class EmployeeAdapter(employeeDiffUtilCallback: EmployeeDiffUtilCallback) :
    PagingDataAdapter<Employee, EmployeeViewHolder>(employeeDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}