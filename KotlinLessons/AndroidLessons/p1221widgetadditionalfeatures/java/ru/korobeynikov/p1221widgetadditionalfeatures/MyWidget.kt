package ru.korobeynikov.p1221widgetadditionalfeatures

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class MyWidget : AppWidgetProvider() {

    private val updateAllWidgets = "update_all_widgets"

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun updateWidget(ctx: Context?, appWidgetManager: AppWidgetManager?, widgetID: Int) {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val widgetView = RemoteViews(ctx?.packageName, R.layout.widget)
        widgetView.setTextViewText(R.id.tvTime, sdf.format(Date(System.currentTimeMillis())))
        val updateIntent = Intent(ctx, MyWidget::class.java)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(widgetID))
        val pIntent = PendingIntent.getBroadcast(ctx, widgetID, updateIntent, 0)
        widgetView.setOnClickPendingIntent(R.id.tvTime, pIntent)
        appWidgetManager?.updateAppWidget(widgetID, widgetView)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (appWidgetIds != null)
            for (i in appWidgetIds)
                updateWidget(context, appWidgetManager, i)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(isDisabled: Boolean, context: Context?) {
        val intent = Intent(context, MyWidget::class.java)
        intent.action = updateAllWidgets
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (isDisabled)
            alarmManager.cancel(pIntent)
        else
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pIntent)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        setAlarm(false, context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        setAlarm(true, context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action.equals(updateAllWidgets, true))
            context?.let {
                val thisAppWidget = ComponentName(it.packageName, javaClass.name)
                val appWidgetManager = AppWidgetManager.getInstance(it)
                val ids = appWidgetManager.getAppWidgetIds(thisAppWidget)
                for (appWidgetID in ids)
                    updateWidget(context, appWidgetManager, appWidgetID)
            }
    }
}