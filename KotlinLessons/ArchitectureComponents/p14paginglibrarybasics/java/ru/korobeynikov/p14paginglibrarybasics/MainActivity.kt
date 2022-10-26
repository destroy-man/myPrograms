package ru.korobeynikov.p14paginglibrarybasics

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
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(10).build()
        val pagedList = PagedList.Builder(dataSource, config)
            .setNotifyExecutor(MainThreadExecutor())
            .setFetchExecutor(Executors.newSingleThreadExecutor()).build()
        val adapter = EmployeeAdapter(diffUtilCallback)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.submitList(pagedList)
        recyclerView.adapter = adapter
    }
}