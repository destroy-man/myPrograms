package ru.korobeynikov.p1211listwidget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MyProvider : AppWidgetProvider() {

    companion object {
        const val ITEM_POSITION = "item_position"
    }

    private val actionOnClick = "ru.korobeynikov.p1211listwidget.itemonclick"
    private val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (appWidgetIds != null)
            for (i in appWidgetIds)
                updateWidget(context, appWidgetManager, i)
    }

    private fun updateWidget(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        val rv = RemoteViews(context?.packageName, R.layout.widget)
        setUpdateTV(rv, context, appWidgetId)
        setList(rv, context, appWidgetId)
        setListClick(rv, context, appWidgetId)
        appWidgetManager?.updateAppWidget(appWidgetId, rv)
        appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvList)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setUpdateTV(rv: RemoteViews, context: Context?, appWidgetId: Int) {
        rv.setTextViewText(R.id.tvUpdate, sdf.format(Date(System.currentTimeMillis())))
        val updIntent = Intent(context, MyProvider::class.java)
        updIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        val updPIntent = PendingIntent.getBroadcast(context, appWidgetId, updIntent, 0)
        rv.setOnClickPendingIntent(R.id.tvUpdate, updPIntent)
    }

    private fun setList(rv: RemoteViews, context: Context?, appWidgetId: Int) {
        with(Intent(context, MyService::class.java)) {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            data = Uri.parse(this.toUri(Intent.URI_INTENT_SCHEME))
            rv.setRemoteAdapter(R.id.lvList, this)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setListClick(rv: RemoteViews, context: Context?, appWidgetId: Int) {
        val listClickIntent = Intent(context, MyProvider::class.java)
        listClickIntent.action = actionOnClick
        val listClickPIntent = PendingIntent.getBroadcast(context, 0, listClickIntent, 0)
        rv.setPendingIntentTemplate(R.id.lvList, listClickPIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action.equals(actionOnClick, true)) {
            val itemPos = intent?.getIntExtra(ITEM_POSITION, -1)
            if (itemPos != -1)
                Toast.makeText(context, "Clicked on item $itemPos", Toast.LENGTH_SHORT).show()
        }
    }
}