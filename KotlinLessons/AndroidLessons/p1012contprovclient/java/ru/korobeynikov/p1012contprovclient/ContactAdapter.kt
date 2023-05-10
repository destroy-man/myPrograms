package ru.korobeynikov.p1012contprovclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textName: TextView
        val textEmail: TextView

        init {
            textName = view.findViewById(R.id.textName)
            textEmail = view.findViewById(R.id.textEmail)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contact = dataSet[position].split("-")
        viewHolder.textName.text = contact[0]
        viewHolder.textEmail.text = contact[1]
    }

    override fun getItemCount() = dataSet.size
}