package ru.korobeynikov.p0551headerfooter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeaderFooterAdapter(private val text: String, private val isHeader: Boolean) :
    RecyclerView.Adapter<HeaderFooterAdapter.HeaderFooterViewHolder>() {

    class HeaderFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvText: TextView

        init {
            tvText = view.findViewById(R.id.tvText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HeaderFooterViewHolder {
        var view = LayoutInflater.from(viewGroup.context).inflate(R.layout.header, viewGroup, false)
        if (!isHeader)
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.footer, viewGroup, false)
        return HeaderFooterViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: HeaderFooterViewHolder, position: Int) {
        viewHolder.tvText.text = text
    }

    override fun getItemCount() = 1
}