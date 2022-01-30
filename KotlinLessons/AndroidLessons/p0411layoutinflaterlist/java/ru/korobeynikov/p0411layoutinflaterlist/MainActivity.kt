package ru.korobeynikov.p0411layoutinflaterlist

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*

class MainActivity : AppCompatActivity() {

    val name=arrayOf("Иван","Марья","Петр","Антон","Даша","Борис","Костя","Игорь")
    val position=arrayOf("Программер","Бухгалтер","Программер","Программер","Бухгалтер","Директор","Программер","Охранник")
    val salary=arrayOf(13000,10000,13000,13000,10000,15000,13000,8000)
    var colors:Array<Int> = Array(2){0}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colors[0]=Color.parseColor("#559966CC")
        colors[1]=Color.parseColor("#55336699")
        val ltInflater=layoutInflater
        for(i in name.indices){
            Log.d("myLogs","i = "+i)
            val item=ltInflater.inflate(R.layout.item,linLayout,false)
            val tvName=item.findViewById<TextView>(R.id.tvName)
            tvName.text=name[i]
            val tvPosition=item.findViewById<TextView>(R.id.tvPosition)
            tvPosition.text="Должность: "+position[i]
            val tvSalary=item.findViewById<TextView>(R.id.tvSalary)
            tvSalary.text="Оклад: "+salary[i]
            item.layoutParams.width=ViewGroup.LayoutParams.MATCH_PARENT
            item.setBackgroundColor(colors[i%2])
            linLayout.addView(item)
        }
    }
}