package com.korobeynikov.p0511simpleadapterdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val CM_DELETE_ID=1
    val ATTRIBUTE_NAME_TEXT="text"
    val ATTRIBUTE_NAME_IMAGE="image"
    lateinit var sAdapter:SimpleAdapter
    lateinit var data:ArrayList<Map<String,Object>>
    lateinit var m:Map<String,Object>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data=ArrayList<Map<String,Object>>()
        for(i in 1..4){
            m=HashMap<String,Object>()
            (m as HashMap<String,Object>)[ATTRIBUTE_NAME_TEXT]=("sometext "+i) as Object
            (m as HashMap<String,Object>)[ATTRIBUTE_NAME_IMAGE]=R.drawable.ic_launcher_foreground as Object
            data.add(m)
        }
        val from=arrayOf(ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_IMAGE)
        val to=arrayOf(R.id.tvText,R.id.ivImg)
        sAdapter=SimpleAdapter(this,data,R.layout.item,from,to.toIntArray())
        lvSimple.adapter=sAdapter
        registerForContextMenu(lvSimple)

        btn.setOnClickListener {
            onButtonClick()
        }
    }

    fun onButtonClick(){
        m=HashMap<String,Object>()
        (m as HashMap<String,Object>)[ATTRIBUTE_NAME_TEXT]=("sometext "+(data.size+1)) as Object
        (m as HashMap<String,Object>)[ATTRIBUTE_NAME_IMAGE]=R.drawable.ic_launcher_foreground as Object
        data.add(m)
        sAdapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(0,CM_DELETE_ID,0,"Удалить запись")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.itemId==CM_DELETE_ID){
            val acmi=item.menuInfo as AdapterView.AdapterContextMenuInfo
            data.removeAt(acmi.position)
            sAdapter.notifyDataSetChanged()
            return true
        }
        return super.onContextItemSelected(item)
    }
}