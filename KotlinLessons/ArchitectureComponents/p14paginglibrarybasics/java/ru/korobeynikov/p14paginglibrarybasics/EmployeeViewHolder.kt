package ru.korobeynikov.p14paginglibrarybasics

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var textView: TextView

    fun bind(employee: Employee?) {
        textView = view.findViewById(R.id.textView)
        textView.text = "${employee?.name}    ${employee?.salary}"
    }
}