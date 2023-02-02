package ru.korobeynikov.p0431simplelistchoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0431simplelistchoice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val names = arrayListOf<String>()
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val listSelectedNames = binding.listSelectedNames
        val rvMain = binding.rvMain
        val layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvMain.context, layoutManager.orientation)
        rvMain.addItemDecoration(dividerItemDecoration)
        rvMain.adapter = NameAdapter(resources.getStringArray(R.array.names))
        rvMain.addOnItemTouchListener(RecyclerTouchListener(this,
            object : RecyclerTouchListener.ClickListener {
                override fun onClick(view: View, position: Int) {
                    val name = (rvMain.adapter as NameAdapter).getItem(position)
                    if (names.contains(name))
                        names.remove(name)
                    else
                        names.add(name)
                    val listNames = "$names".replace("[", "").replace("]", "")
                    if (listNames.isNotEmpty())
                        listSelectedNames.text = listNames
                    else
                        listSelectedNames.text = resources.getText(R.string.textViewSelectNothing)
                }
            }))
    }
}