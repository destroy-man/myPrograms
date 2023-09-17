package ru.korobeynikov.p14paginglibrarybasics.employee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.korobeynikov.p14paginglibrarybasics.R

class EmployeeAdapter(diffUtilCallback: DiffUtil.ItemCallback<Employee>) :
    PagingDataAdapter<Employee, EmployeeAdapter.ViewHolder>(diffUtilCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textName: TextView
        val textSalary: TextView

        init {
            textName = view.findViewById(R.id.textName)
            textSalary = view.findViewById(R.id.textSalary)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = getItem(position)
        holder.textName.text = employee?.name
        holder.textSalary.text = employee?.salary.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee, parent, false)
        return ViewHolder(view)
    }
}