package ru.korobeynikov.p1171widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = MyAppWidget()

    val logTag = "myLogs"

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(logTag, "onEnabled")
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(logTag, "onUpdate ${appWidgetIds.contentToString()}")
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d(logTag, "onDeleted ${appWidgetIds.contentToString()}")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(logTag, "onDisabled")
    }
}