package com.korobeynikov.p0421simplelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val names=arrayOf("Иван","Марья","Петр","Антон","Даша","Борис","Костя","Игорь","Анна","Денис","Андрей")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter=ArrayAdapter<String>(this,R.layout.my_list_item,names)
        lvMain.adapter=adapter
    }
}