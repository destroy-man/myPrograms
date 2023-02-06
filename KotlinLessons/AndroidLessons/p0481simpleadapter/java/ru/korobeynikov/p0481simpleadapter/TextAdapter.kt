package ru.korobeynikov.p0481simpleadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextAdapter(private val dataSet: Array<String>, private val checked: BooleanArray) :
    RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cbChecked: CheckBox
        val tvText: TextView

        init {
            cbChecked = view.findViewById(R.id.cbChecked)
            tvText = view.findViewById(R.id.tvText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cbChecked = viewHolder.cbChecked
        cbChecked.isChecked = checked[position]
        cbChecked.text = dataSet[position]
        viewHolder.tvText.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}