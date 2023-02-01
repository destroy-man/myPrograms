package ru.korobeynikov.p0421simplelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0421simplelist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val names = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя",
        "Игорь", "Анна", "Денис", "Андрей")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val rvMain = binding.rvMain
        val layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvMain.context, layoutManager.orientation)
        rvMain.addItemDecoration(dividerItemDecoration)
        rvMain.adapter = NameAdapter(names)
    }
}