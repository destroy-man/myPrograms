package com.korobeynikov.p0501simpleadaptercustom2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val ATTRIBUTE_NAME_TEXT="text"
    val ATTRIBUTE_NAME_PB="pb"
    val ATTRIBUTE_NAME_LL="ll"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val load=arrayOf(41,48,22,35,30,67,51,88)
        val data=ArrayList<Map<String,Object>>(load.size)
        var m:Map<String,Object>
        for(i in load.indices){
            m=HashMap<String,Object>()
            m[ATTRIBUTE_NAME_TEXT]=("Day "+(i+1)+". Load: "+load[i]+"%") as Object
            m[ATTRIBUTE_NAME_PB]=load[i] as Object
            m[ATTRIBUTE_NAME_LL]=load[i] as Object
            data.add(m)
        }
        val from=arrayOf(ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_PB,ATTRIBUTE_NAME_LL)
        val to=arrayOf(R.id.tvLoad,R.id.pbLoad,R.id.llLoad)
        val sAdapter=SimpleAdapter(this,data,R.layout.item,from,to.toIntArray())
        sAdapter.setViewBinder(MyViewBinder())
        lvSimple.adapter=sAdapter
    }

    inner class MyViewBinder:SimpleAdapter.ViewBinder{

        val red=resources.getColor(R.color.Red)
        val orange=resources.getColor(R.color.Orange)
        val green=resources.getColor(R.color.Green)

        override fun setViewValue(view: View?, data: Any?, textRepresentation: String?): Boolean {
            var i=0
            when(view?.id){
                R.id.llLoad->{
                    i=(data as Integer).toInt()
                    if(i<40)
                        view.setBackgroundColor(green)
                    else if(i<70)
                        view.setBackgroundColor(orange)
                    else
                        view.setBackgroundColor(red)
                    return true
                }
                R.id.pbLoad->{
                    i=(data as Integer).toInt()
                    (view as ProgressBar).setProgress(i)
                    return true
                }
            }
            return false
        }

    }
}