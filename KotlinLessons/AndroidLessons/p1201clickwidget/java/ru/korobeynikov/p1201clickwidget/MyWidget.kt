package ru.korobeynikov.p1201clickwidget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class MyWidget : AppWidgetProvider() {

    companion object {

        const val ACTION_CHANGE = "ru.korobeynikov.p1201clickwidget.change_count"
        var needUpdateTime = true

        @SuppressLint("UnspecifiedImmutableFlag")
        fun updateWidget(ctx: Context?, appWidgetManager: AppWidgetManager?, widgetID: Int) {
            val sp = ctx?.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)
            val timeFormat =
                sp?.getString(ConfigActivity.WIDGET_TIME_FORMAT + widgetID, null) ?: return
            val sdf = SimpleDateFormat(timeFormat, Locale.getDefault())
            val currentTime = sdf.format(Date(System.currentTimeMillis()))
            val count = sp.getInt(ConfigActivity.WIDGET_COUNT + widgetID, 0).toString()
            // Заполнение текстовых полей
            val widgetView = RemoteViews(ctx.packageName, R.layout.widget)
            if (needUpdateTime)
                widgetView.setTextViewText(R.id.tvTime, currentTime)
            widgetView.setTextViewText(R.id.tvCount, count)
            // Конфигурационный экран
            val configIntent = Intent(ctx, ConfigActivity::class.java)
            configIntent.action = AppWidgetManager.ACTION_APPWIDGET_CONFIGURE
            configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            var pIntent = PendingIntent.getActivity(ctx, widgetID, configIntent, 0)
            widgetView.setOnClickPendingIntent(R.id.tvPressConfig, pIntent)
            // Обновление виджета
            val updateIntent = Intent(ctx, MyWidget::class.java)
            updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(widgetID))
            pIntent = PendingIntent.getBroadcast(ctx, widgetID, updateIntent, 0)
            widgetView.setOnClickPendingIntent(R.id.tvPressUpdate, pIntent)
            // Счетчик нажатий
            val countIntent = Intent(ctx, MyWidget::class.java)
            countIntent.action = ACTION_CHANGE
            countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            pIntent = PendingIntent.getBroadcast(ctx, widgetID, countIntent, 0)
            widgetView.setOnClickPendingIntent(R.id.tvPressCount, pIntent)
            // Открытие сайта
            val webIntent = Intent(ctx, WebActivity::class.java)
            webIntent.action = Intent.ACTION_VIEW
            webIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            pIntent = PendingIntent.getActivity(ctx, widgetID, webIntent, 0)
            widgetView.setOnClickPendingIntent(R.id.tvPressWeb, pIntent)
            // Обновляем виджет
            appWidgetManager?.updateAppWidget(widgetID, widgetView)
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (appWidgetIds != null)
            for (i in appWidgetIds)
                updateWidget(context, appWidgetManager, i)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        val editor = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)?.edit()
        if (appWidgetIds != null)
            for (widgetID in appWidgetIds) {
                editor?.remove(ConfigActivity.WIDGET_TIME_FORMAT + widgetID)
                editor?.remove(ConfigActivity.WIDGET_COUNT + widgetID)
            }
        editor?.apply()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action.equals(ACTION_CHANGE, true)) {
            var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
            val extras = intent?.extras
            if (extras != null)
                mAppWidgetId =
                    extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                context?.let {
                    val sp = it.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)
                    var cnt = sp.getInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, 0)
                    sp.edit().putInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, ++cnt).apply()
                    needUpdateTime = false
                    updateWidget(it, AppWidgetManager.getInstance(it), mAppWidgetId)
                }
            }
        } else {
            var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
            val extras = intent?.extras
            if (extras != null)
                mAppWidgetId = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS)?.get(0)
                    ?: AppWidgetManager.INVALID_APPWIDGET_ID
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                needUpdateTime = true
                updateWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId)
            }
        }
    }
}