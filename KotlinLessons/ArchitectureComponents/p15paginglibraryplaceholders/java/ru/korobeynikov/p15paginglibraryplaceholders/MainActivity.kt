package ru.korobeynikov.p15paginglibraryplaceholders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diffUtilCallback = EmployeeDiffUtilCallback()
        val dataSource = MyPositionalDataSource(EmployeeStorage())
        val configBuilder = PagedList.Config.Builder()
        val config = configBuilder.setEnablePlaceholders(true).setPageSize(5).build()
        val pagedList = PagedList.Builder(dataSource, config)
            .setNotifyExecutor(MainThreadExecutor())
            .setFetchExecutor(Executors.newSingleThreadExecutor()).build()
        val adapter = EmployeeAdapter(diffUtilCallback)
        adapter.submitList(pagedList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}