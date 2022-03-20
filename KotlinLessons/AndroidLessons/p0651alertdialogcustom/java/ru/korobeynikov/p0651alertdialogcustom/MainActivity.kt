package ru.korobeynikov.p0651alertdialogcustom

import android.app.AlertDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val DIALOG=1
    var btn:Int=0
    lateinit var view:LinearLayout
    val sdf=SimpleDateFormat("HH:mm:ss")
    lateinit var tvCount:TextView
    lateinit var textViews:ArrayList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViews=ArrayList(10)
    }

    fun onclick(v:View){
        btn=v.id
        showDialog(DIALOG)
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        val adb=AlertDialog.Builder(this)
        adb.setTitle("Custom dialog")
        view=layoutInflater.inflate(R.layout.dialog,null) as LinearLayout
        adb.setView(view)
        tvCount=view.findViewById(R.id.tvCount)
        return adb.create()
    }

    protected override fun onPrepareDialog(id: Int, dialog: Dialog?) {
        super.onPrepareDialog(id, dialog)
        if(id==DIALOG){
            val tvTime=dialog?.window?.findViewById<TextView>(R.id.tvTime)
            tvTime?.text=sdf.format(Date(System.currentTimeMillis()))
            if(btn==R.id.btnAdd){
                val tv=TextView(this)
                view.addView(tv,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
                tv.text="TextView ${textViews.size+1}"
                textViews.add(tv)
            }
            else{
                if(textViews.size>0){
                    val tv=textViews[textViews.size-1]
                    view.removeView(tv)
                    textViews.remove(tv)
                }
            }
            tvCount.text="Кол-во TextView = ${textViews.size}"
        }
    }
}