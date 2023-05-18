package ru.korobeynikov.p1171simplewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log

class MyWidget : AppWidgetProvider() {

    private val logTag = "myLogs"

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d(logTag, "onEnabled")
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(logTag, "onUpdate ${appWidgetIds.contentToString()}")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(logTag, "onDelete ${appWidgetIds.contentToString()}")
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d(logTag, "onDisabled")
    }
}