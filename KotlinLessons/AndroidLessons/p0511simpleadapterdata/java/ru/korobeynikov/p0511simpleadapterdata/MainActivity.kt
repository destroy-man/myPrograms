package ru.korobeynikov.p0511simpleadapterdata

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private val CM_DELETE_ID=1
    }

    val ATTRIBUTE_NAME_TEXT="text"
    val ATTRIBUTE_NAME_IMAGE="image"
    lateinit var sAdapter:SimpleAdapter
    lateinit var data:ArrayList<Map<String,Any>>
    lateinit var m:Map<String,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data=ArrayList<Map<String,Any>>()
        for(i in 1..4){
            m=HashMap<String,Any>()
            (m as HashMap<String,Any>).put(ATTRIBUTE_NAME_TEXT,"sometext "+i)
            (m as HashMap<String,Any>).put(ATTRIBUTE_NAME_IMAGE,R.mipmap.ic_launcher)
            data.add(m)
        }
        val from=arrayOf(ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_IMAGE)
        val to=arrayOf(R.id.tvText,R.id.ivImg)
        sAdapter=SimpleAdapter(this,data,R.layout.item,from,to.toIntArray())
        lvSimple.adapter=sAdapter
        registerForContextMenu(lvSimple)
    }

    fun onButtonClick(v:View){
        m=HashMap<String,Any>()
        (m as HashMap<String,Any>).put(ATTRIBUTE_NAME_TEXT,"sometext "+(data.size+1))
        (m as HashMap<String,Any>).put(ATTRIBUTE_NAME_IMAGE,R.mipmap.ic_launcher)
        data.add(m)
        sAdapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0,CM_DELETE_ID,0,"Удалить запись")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.itemId== CM_DELETE_ID){
            val acmi=item.menuInfo as AdapterView.AdapterContextMenuInfo
            data.removeAt(acmi.position)
            sAdapter.notifyDataSetChanged()
            return true
        }
        return super.onContextItemSelected(item)
    }
}