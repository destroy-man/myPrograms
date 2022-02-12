package ru.korobeynikov.p0441simplelistevents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter=ArrayAdapter.createFromResource(this,R.array.names,android.R.layout.simple_list_item_1)
        lvMain.adapter=adapter
        lvMain.setOnItemClickListener { adapterView, view, position, id ->
            Log.d(LOG_TAG,"itemClick: position = "+position+", id = "+id)
        }
        lvMain.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                Log.d(LOG_TAG,"itemSelect: position = "+position+", id = "+id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d(LOG_TAG,"itemSelect: nothing")
            }
        }
        lvMain.setOnScrollListener(object:AbsListView.OnScrollListener{
            override fun onScrollStateChanged(p0: AbsListView?, scrollState: Int) {
                Log.d(LOG_TAG,"scrollState = "+scrollState)
            }

            override fun onScroll(p0: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                Log.d(LOG_TAG,"scroll: firstVisibleItem = "+firstVisibleItem+", visibleItemCount = "+
                        visibleItemCount+", totalItemCount = "+totalItemCount)
            }
        })
    }
}