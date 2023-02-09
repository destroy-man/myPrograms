package ru.korobeynikov.p0511simpleadapterdata

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NameAdapter(private val dataSet: ArrayList<String>) : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        val tvText: TextView

        init {
            tvText = view.findViewById(R.id.tvText)
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                         menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, 1, 0, "Удалить запись")
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvText.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}