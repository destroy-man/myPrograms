package ru.korobeynikov.p1211listwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import java.text.SimpleDateFormat
import java.util.*

class MyFactory(private val ctx: Context, intent: Intent?) : RemoteViewsService.RemoteViewsFactory {

    private var sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private var widgetID = 0
    private lateinit var data: ArrayList<String>

    init {
        widgetID = intent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID) ?: 0
    }

    override fun onCreate() {
        data = ArrayList()
    }

    override fun getCount() = data.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewAt(position: Int): RemoteViews {
        val clickIntent = Intent()
        clickIntent.putExtra(MyProvider.ITEM_POSITION, position)
        with(RemoteViews(ctx.packageName, R.layout.item)) {
            setTextViewText(R.id.tvItemText, data[position])
            setOnClickFillInIntent(R.id.tvItemText, clickIntent)
            return this
        }
    }

    override fun getViewTypeCount() = 1

    override fun hasStableIds() = true

    override fun onDataSetChanged() {
        with(data) {
            clear()
            add(sdf.format(Date(System.currentTimeMillis())))
            add(this@MyFactory.hashCode().toString())
            add(widgetID.toString())
            for (i in 3 until 15)
                add("Item $i")
        }
    }

    override fun onDestroy() {}
}