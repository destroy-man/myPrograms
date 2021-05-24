package com.korobeynikov.p0411layoutinflaterlist

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity() {

    val name=arrayOf("Иван","Марья","Петр","Антон","Даша","Борис","Костя","Игорь")
    val position=arrayOf("Программер","Бухгалтер","Программер","Программер","Бухгалтер","Директор",
        "Программер","Охранник")
    val salary=arrayOf(13000,10000,13000,13000,10000,15000,13000,8000)
    val colors:Array<Int> = Array(2,{0})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colors[0]=Color.parseColor("#559966CC")
        colors[1]=Color.parseColor("#55336699")
        for(i in name.indices){
            Log.d("myLogs","i = "+i)
            val item=layoutInflater.inflate(R.layout.item,linLayout,false)
            item.tvName.text=name[i]
            item.tvPosition.text="Должность: "+position[i]
            item.tvSalary.text="Оклад: "+salary[i]
            item.layoutParams.width=LinearLayout.LayoutParams.MATCH_PARENT
            item.setBackgroundColor(colors[i%2])
            linLayout.addView(item)
        }
    }
}