package ru.korobeynikov.p0461expandablelistevents

import android.content.Context
import android.widget.SimpleExpandableListAdapter

class AdapterHelper {

    val ATTR_GROUP_NAME="groupName"
    val ATTR_PHONE_NAME="phoneName"
    val groups=arrayOf("HTC","Samsung","LG")
    val phonesHTC=arrayOf("Sensation","Desire","Wildfire","Hero")
    val phonesSams=arrayOf("Galaxy S II","Galaxy Nexus","Wave")
    val phonesLG=arrayOf("Optimus","Optimus Link","Optimus Black","Optimus One")
    lateinit var groupData: ArrayList<Map<String,String>>
    lateinit var childDataItem: ArrayList<Map<String,String>>
    lateinit var childData: ArrayList<ArrayList<Map<String,String>>>
    lateinit var m: Map<String,String>
    lateinit var ctx: Context

    constructor(_ctx:Context){
        ctx=_ctx
    }

    lateinit var adapter: SimpleExpandableListAdapter

    @JvmName("getAdapter1")
    fun getAdapter():SimpleExpandableListAdapter{
        groupData=ArrayList<Map<String,String>>()
        for(group in groups){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put(ATTR_GROUP_NAME,group)
            groupData.add(m)
        }
        val groupFrom=arrayOf(ATTR_GROUP_NAME)
        val groupTo=arrayOf(android.R.id.text1)
        childData=ArrayList<ArrayList<Map<String,String>>>()
        childDataItem=ArrayList<Map<String,String>>()
        for(phone in phonesHTC){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put(ATTR_PHONE_NAME,phone)
            childDataItem.add(m)
        }
        childData.add(childDataItem)
        childDataItem=ArrayList<Map<String,String>>()
        for(phone in phonesSams){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put(ATTR_PHONE_NAME,phone)
            childDataItem.add(m)
        }
        childData.add(childDataItem)
        childDataItem=ArrayList<Map<String,String>>()
        for(phone in phonesLG){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put(ATTR_PHONE_NAME,phone)
            childDataItem.add(m)
        }
        childData.add(childDataItem)
        val childFrom=arrayOf(ATTR_PHONE_NAME)
        val childTo=arrayOf(android.R.id.text1)
        adapter= SimpleExpandableListAdapter(ctx,groupData,android.R.layout.simple_expandable_list_item_1,
            groupFrom,groupTo.toIntArray(),childData,android.R.layout.simple_list_item_1,childFrom,childTo.toIntArray())
        return adapter
    }

    fun getGroupText(groupPos:Int): String? {
        return (adapter.getGroup(groupPos) as Map<String,String>).get(ATTR_GROUP_NAME)
    }

    fun getChildText(groupPos:Int,childPos:Int): String? {
        return (adapter.getChild(groupPos,childPos) as Map<String,String>).get(ATTR_PHONE_NAME)
    }

    fun getGroupChildText(groupPos:Int,childPos:Int):String{
        return getGroupText(groupPos)+" "+getChildText(groupPos,childPos)
    }
}