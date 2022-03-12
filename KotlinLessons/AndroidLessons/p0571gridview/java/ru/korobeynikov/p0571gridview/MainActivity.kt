package ru.korobeynikov.p0571gridview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val data=arrayOf("a","b","c","d","e","f","g","h","i","j","k")
    lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter=ArrayAdapter<String>(this,R.layout.item,R.id.tvText,data)
        gvMain.adapter=adapter
        adjustGridView()
    }

    private fun adjustGridView(){
        gvMain.numColumns=GridView.AUTO_FIT
        gvMain.columnWidth=80
        gvMain.verticalSpacing=5
        gvMain.horizontalSpacing=5
        gvMain.stretchMode=GridView.STRETCH_SPACING_UNIFORM
    }
}