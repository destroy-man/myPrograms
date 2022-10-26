package ru.korobeynikov.p16paginglibraryboundarycallback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        val diffUtilCallback = EmployeeDiffUtilCallback()
        val sourceFactory = employeeDao.getAll()
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(10).build()
        val pagedListLiveData = LivePagedListBuilder(sourceFactory, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor()).build()
        val adapter = EmployeeAdapter(diffUtilCallback)
        pagedListLiveData.observe(this) { employees ->
            Log.d(TAG, "submit PagedList")
            adapter.submitList(employees)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}