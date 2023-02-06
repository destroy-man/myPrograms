package ru.korobeynikov.p0491simpleadaptercustom1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChartAdapter(private val dataSet: IntArray, private val days: Array<String?>) :
    RecyclerView.Adapter<ChartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imIvg: ImageView
        val tvValue: TextView
        val tvText: TextView

        init {
            imIvg = view.findViewById(R.id.ivImg)
            tvValue = view.findViewById(R.id.tvValue)
            tvText = view.findViewById(R.id.tvText)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val imIvg = viewHolder.imIvg
        val tvValue = viewHolder.tvValue
        if (dataSet[position] > 0) {
            imIvg.setImageResource(android.R.drawable.arrow_up_float)
            imIvg.setBackgroundColor(Color.GREEN)
            tvValue.setTextColor(Color.GREEN)
        } else if (dataSet[position] < 0) {
            imIvg.setImageResource(android.R.drawable.arrow_down_float)
            imIvg.setBackgroundColor(Color.RED)
            tvValue.setTextColor(Color.RED)
        }
        tvValue.text = dataSet[position].toString()
        viewHolder.tvText.text = days[position]
    }

    override fun getItemCount() = dataSet.size
}