package ru.korobeynikov.p23androiddatabindingrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.korobeynikov.p23androiddatabindingrecyclerview.databinding.EmployeeItemBinding
import java.util.*

class EmployeeAdapter : RecyclerView.Adapter<EmployeeHolder>() {

    private val items = LinkedList<Employee>()

    fun setData(data: List<Employee>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<EmployeeItemBinding>(inflater, R.layout.employee_item, parent, false)
        return EmployeeHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}