package ru.korobeynikov.p14paginglibrarybasics.employee

import androidx.recyclerview.widget.DiffUtil

class EmployeeDiffUtilCallback : DiffUtil.ItemCallback<Employee>() {

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee) =
        oldItem.name == newItem.name && oldItem.salary == newItem.salary
}