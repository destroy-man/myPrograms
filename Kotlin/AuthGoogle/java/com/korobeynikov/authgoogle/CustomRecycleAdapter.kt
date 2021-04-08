package com.korobeynikov.authgoogle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class CustomRecycleAdapter(private val names:List<String>):RecyclerView.Adapter<CustomRecycleAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var smallTextView:TextView?=null
        init{
            smallTextView=itemView.findViewById(R.id.textViewSmall)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.smallTextView?.text=names[position]
    }

    override fun getItemCount(): Int {
        return names.size
    }

}