package com.korobeynikov.p0481simpleadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val ATTRIBUTE_NAME_TEXT="text"
    val ATTRIBUTE_NAME_CHECKED="checked"
    val ATTRIBUTE_NAME_IMAGE="image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val texts=arrayOf("sometext 1","sometext 2","sometext 3","sometext 4","sometext 5")
        val checked=arrayOf(true,false,false,true,false)
        val img=R.drawable.ic_launcher_foreground
        val data=ArrayList<Map<String,Object>>(texts.size)
        var m:Map<String,Object>? = null
        for(i in texts.indices){
            m=HashMap<String,Object>()
            m[ATTRIBUTE_NAME_TEXT]=texts[i] as Object
            m[ATTRIBUTE_NAME_CHECKED]=checked[i] as Object
            m[ATTRIBUTE_NAME_IMAGE]=img as Object
            data.add(m)
        }
        val from=arrayOf(ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_CHECKED,ATTRIBUTE_NAME_CHECKED,ATTRIBUTE_NAME_TEXT)
        val to=arrayOf(R.id.tvText,R.id.cbChecked,R.id.ivImg,R.id.cbChecked)
        val sAdapter=SimpleAdapter(this,data,R.layout.item,from,to.toIntArray())
        lvSimple.adapter=sAdapter
    }
}