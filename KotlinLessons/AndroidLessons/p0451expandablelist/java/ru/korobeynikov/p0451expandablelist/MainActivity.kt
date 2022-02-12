package ru.korobeynikov.p0451expandablelist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleExpandableListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val groups=arrayOf("HTC","Samsung","LG")
    val phonesHTC=arrayOf("Sensation","Desire","Wildfire","Hero")
    val phonesSams=arrayOf("Galaxy S II","Galaxy Nexus","Wave")
    val phonesLG=arrayOf("Optimus","Optimus Link","Optimus Black","Optimus One")
    lateinit var groupData: ArrayList<Map<String,String>>
    lateinit var childDataItem: ArrayList<Map<String,String>>
    lateinit var childData: ArrayList<ArrayList<Map<String,String>>>
    lateinit var m: Map<String,String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        groupData=ArrayList<Map<String,String>>()
        for(group in groups){
            m=HashMap<String,String>()
            (m as HashMap<String, String>).put("groupName",group)
            groupData.add(m)
        }
        val groupFrom=arrayOf("groupName")
        val groupTo=arrayOf(android.R.id.text1)
        childData=ArrayList<ArrayList<Map<String,String>>>()
        childDataItem=ArrayList<Map<String,String>>()
        for(phone in phonesHTC){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put("phoneName",phone)
            childDataItem.add(m)
        }
        childData.add(childDataItem)
        childDataItem=ArrayList<Map<String,String>>()
        for(phone in phonesSams){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put("phoneName",phone)
            childDataItem.add(m)
        }
        childData.add(childDataItem)
        childDataItem=ArrayList<Map<String,String>>()
        for(phone in phonesLG){
            m=HashMap<String,String>()
            (m as HashMap<String,String>).put("phoneName",phone)
            childDataItem.add(m)
        }
        childData.add(childDataItem)
        val childFrom=arrayOf("phoneName")
        val childTo=arrayOf(android.R.id.text1)
        val adapter=SimpleExpandableListAdapter(this,groupData,android.R.layout.simple_expandable_list_item_1,
            groupFrom,groupTo.toIntArray(),childData,android.R.layout.simple_list_item_1,childFrom,childTo.toIntArray())
        elvMain.setAdapter(adapter)
    }
}