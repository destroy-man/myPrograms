package ru.korobeynikov.p1211listwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyFactory(ctx:Context,intent:Intent):RemoteViewsService.RemoteViewsFactory {

    lateinit var data:ArrayList<String>
    var context=ctx
    val sdf=SimpleDateFormat("HH:mm:ss")
    var widgetID=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)

    override fun onCreate() {
        data=ArrayList()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rView=RemoteViews(context.packageName,R.layout.item)
        rView.setTextViewText(R.id.tvItemText, data[position])
        val clickIntent=Intent()
        clickIntent.putExtra(MyProvider.ITEM_POSITION,position)
        rView.setOnClickFillInIntent(R.id.tvItemText,clickIntent)
        return rView
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        data.clear()
        data.add(sdf.format(Date(System.currentTimeMillis())))
        data.add(hashCode().toString())
        data.add(widgetID.toString())
        for(i in 3..14)
            data.add("Item $i")
    }

    override fun onDestroy() {}
}