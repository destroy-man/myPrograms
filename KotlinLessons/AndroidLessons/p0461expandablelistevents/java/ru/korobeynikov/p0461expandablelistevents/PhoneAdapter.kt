package ru.korobeynikov.p0461expandablelistevents

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhoneAdapter(private val groupPhones: MutableList<GroupPhones>, private val tvInfo: TextView)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val logTag = "myLogs"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Types.PARENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.group_row, parent, false)
            GroupViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.phone_row, parent, false)
            PhoneViewHolder(view)
        }
    }

    override fun getItemCount() = groupPhones.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val group = groupPhones[position]
        if (group.type == Types.PARENT) {
            (holder as GroupViewHolder).apply {
                groupName.text = group.name
                down.setOnClickListener {
                    expandOrCollapseGroup(group, position)
                }
                groupName.setOnClickListener {
                    Log.d(logTag, "groupClick: groupPosition = $position")
                }
            }
        } else {
            (holder as PhoneViewHolder).apply {
                name.text = group.name
                name.setOnClickListener {
                    Log.d(logTag, "childClick: groupPosition = ${group.parentPosition}, " +
                            "childPosition = $position")
                    tvInfo.text = "${group.parentName} ${group.name}"
                }
            }
        }
    }

    private fun expandOrCollapseGroup(group: GroupPhones, position: Int) {
        if (group.isExpanded) {
            collapseGroup(position)
            Log.d(logTag, "groupCollapse: groupPosition = $position")
            tvInfo.text = "Свернули ${group.name}"
        } else {
            expandGroup(position)
            Log.d(logTag, "groupExpand: groupPosition = $position")
            tvInfo.text = "Развернули ${group.name}"
        }
    }

    private fun expandGroup(position: Int) {
        val group = groupPhones[position]
        group.isExpanded = true
        var nextPosition = position
        if (group.type == Types.PARENT) {
            group.phones.forEach { phone ->
                groupPhones.add(++nextPosition,
                    GroupPhones(name = phone.name, type = Types.CHILD, parentPosition = position,
                        parentName = group.name))
            }
            notifyDataSetChanged()
        }
    }

    private fun collapseGroup(position: Int) {
        val group = groupPhones[position]
        group.isExpanded = false
        if (group.type == Types.PARENT) {
            repeat(group.phones.size) {
                groupPhones.removeAt(position + 1)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int) = groupPhones[position].type

    override fun getItemId(position: Int) = position.toLong()

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groupName: TextView = view.findViewById(R.id.groupName)
        val down: ImageView = view.findViewById(R.id.down)
    }

    class PhoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
    }
}