package ru.korobeynikov.p23androiddatabindingrecyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.korobeynikov.p23androiddatabindingrecyclerview.databinding.EmployeeItemBinding

class EmployeeHolder(private val binding: EmployeeItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(employee: Employee) {
        binding.employee = employee
        binding.executePendingBindings()
    }
}