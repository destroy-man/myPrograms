package ru.korobeynikov.p15paginglibraryplaceholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.employee.view.*

class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var textViewName = view.textViewName
    private var textViewSalary = view.textViewSalary

    fun bind(employee: Employee?) {
        if (employee == null) {
            textViewName.setText(R.string.wait)
            textViewSalary.setText(R.string.wait)
        } else {
            textViewName.text = employee.name
            textViewSalary.text = employee.salary.toString()
        }
    }
}