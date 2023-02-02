package ru.korobeynikov.p0441simplelistevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.korobeynikov.p0441simplelistevents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logTag = "myLogs"
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val rvMain = binding.rvMain
        val layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvMain.context, layoutManager.orientation)
        rvMain.addItemDecoration(dividerItemDecoration)
        rvMain.adapter = NameAdapter(resources.getStringArray(R.array.names))
        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
                Log.d(logTag, "scrollState = $scrollState")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.d(logTag, "scroll: dx = $dx, dy = $dy")
            }
        })
    }
}