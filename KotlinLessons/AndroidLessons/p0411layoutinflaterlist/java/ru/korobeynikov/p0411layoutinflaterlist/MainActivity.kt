package ru.korobeynikov.p0411layoutinflaterlist

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0411layoutinflaterlist.databinding.ActivityMainBinding
import ru.korobeynikov.p0411layoutinflaterlist.databinding.ItemBinding

class MainActivity : AppCompatActivity() {

    private val name = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
    private val position = arrayOf("Программер", "Бухгалтер", "Программер", "Программер",
        "Бухгалтер", "Директор", "Программер", "Охранник")
    private val salary = intArrayOf(13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000)
    private val colors = Array(2) { 0 }
    private var lastId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        colors[0] = Color.parseColor("#559966CC")
        colors[1] = Color.parseColor("#55336699")
        val conLayout = binding.conLayout
        for (i in name.indices) {
            Log.d("myLogs", "i = $i")
            val item = DataBindingUtil.inflate<ItemBinding>(layoutInflater, R.layout.item, conLayout, false)
            item.tvName.text = name[i]
            item.tvPosition.text = "Должность: ${position[i]}"
            item.tvSalary.text = "Оклад: ${salary[i]}"
            val view = item.root
            view.setBackgroundColor(colors[i % 2])
            val lp = view.layoutParams as ConstraintLayout.LayoutParams
            lp.startToStart = ConstraintSet.PARENT_ID
            if (lastId == -1)
                lp.topToTop = ConstraintSet.PARENT_ID
            else
                lp.topToBottom = lastId
            view.layoutParams = lp
            lastId = ConstraintLayout.generateViewId()
            view.id = lastId
            conLayout.addView(view)
        }
    }
}