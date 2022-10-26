package ru.korobeynikov.p14paginglibrarybasics

import androidx.recyclerview.widget.DiffUtil

class EmployeeDiffUtilCallback : DiffUtil.ItemCallback<Employee>() {

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.name == newItem.name && oldItem.salary == newItem.salary
    }
}