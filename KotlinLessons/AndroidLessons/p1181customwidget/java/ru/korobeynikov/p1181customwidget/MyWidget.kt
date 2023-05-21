package ru.korobeynikov.p1181customwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews

class MyWidget : AppWidgetProvider() {

    companion object {
        fun updateWidget(context: Context, appWidgetManager: AppWidgetManager,
                         sp: SharedPreferences, widgetID: Int) {
            Log.d(Constants.LOG_TAG, "updateWidget $widgetID")
            val widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + widgetID, null) ?: return
            val widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetID, 0)
            val widgetView = RemoteViews(context.packageName, R.layout.widget)
            widgetView.setTextViewText(R.id.tv, widgetText)
            widgetView.setInt(R.id.tv, "setBackgroundColor", widgetColor)
            appWidgetManager.updateAppWidget(widgetID, widgetView)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d(Constants.LOG_TAG, "onEnabled")
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(Constants.LOG_TAG, "onUpdate ${appWidgetIds.contentToString()}")
        val sp = context.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)
        for (id in appWidgetIds)
            updateWidget(context, appWidgetManager, sp, id)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(Constants.LOG_TAG, "onDeleted ${appWidgetIds.contentToString()}")
        val editor = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)?.edit()
        if (appWidgetIds != null)
            for (widgetID in appWidgetIds) {
                editor?.remove(ConfigActivity.WIDGET_TEXT + widgetID)
                editor?.remove(ConfigActivity.WIDGET_COLOR + widgetID)
            }
        editor?.apply()
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d(Constants.LOG_TAG, "onDisabled")
    }
}