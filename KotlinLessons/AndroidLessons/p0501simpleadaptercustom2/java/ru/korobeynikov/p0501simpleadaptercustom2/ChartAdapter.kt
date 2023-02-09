package ru.korobeynikov.p0501simpleadaptercustom2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ChartAdapter(private val dataSet: IntArray, private val days: Array<String>) :
    RecyclerView.Adapter<ChartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val clLoad: ConstraintLayout
        val tvLoad: TextView
        val pbLoad: ProgressBar

        init {
            clLoad = view.findViewById(R.id.clLoad)
            tvLoad = view.findViewById(R.id.tvLoad)
            pbLoad = view.findViewById(R.id.pbLoad)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (dataSet[position] < 40)
            viewHolder.clLoad.setBackgroundResource(R.color.Green)
        else if (dataSet[position] < 70)
            viewHolder.clLoad.setBackgroundResource(R.color.Orange)
        else
            viewHolder.clLoad.setBackgroundResource(R.color.Red)
        viewHolder.pbLoad.progress = dataSet[position]
        viewHolder.tvLoad.text = days[position]
    }

    override fun getItemCount() = dataSet.size
}