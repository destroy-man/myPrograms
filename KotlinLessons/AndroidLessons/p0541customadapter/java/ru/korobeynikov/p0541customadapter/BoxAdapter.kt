package ru.korobeynikov.p0541customadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BoxAdapter(private val objects: Array<Product?>) : RecyclerView.Adapter<BoxAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cbBox: CheckBox
        val tvDescr: TextView
        val tvPrice: TextView

        init {
            cbBox = view.findViewById(R.id.cbBox)
            tvDescr = view.findViewById(R.id.tvDescr)
            tvPrice = view.findViewById(R.id.tvPrice)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val p = objects[position]
        if (p != null) {
            viewHolder.cbBox.tag = position
            viewHolder.cbBox.setOnCheckedChangeListener { buttonView, isChecked ->
                getProduct(buttonView.tag as Int)?.box = isChecked
            }
            viewHolder.cbBox.isChecked = p.box
            viewHolder.tvDescr.text = p.name
            viewHolder.tvPrice.text = p.price.toString()
        }
    }

    override fun getItemCount() = objects.size

    private fun getProduct(position: Int) = objects[position]

    override fun getItemId(position: Int) = position.toLong()

    fun getBox(): ArrayList<Product?> {
        val box = ArrayList<Product?>()
        for (p in objects)
            if (p != null && p.box)
                box.add(p)
        return box
    }
}