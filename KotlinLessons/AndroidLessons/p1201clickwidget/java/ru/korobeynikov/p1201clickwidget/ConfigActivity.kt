package ru.korobeynikov.p1201clickwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.config.*

class ConfigActivity : AppCompatActivity() {

    companion object{
        val WIDGET_PREF="widget_pref"
        val WIDGET_TIME_FORMAT="widget_time_format_"
        val WIDGET_COUNT="widget_count_"
    }

    var widgetID=AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var resultValue:Intent
    lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras=intent.extras
        if(extras!=null)
            widgetID=extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
        if(widgetID==AppWidgetManager.INVALID_APPWIDGET_ID)finish()
        resultValue=Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID)
        setResult(RESULT_CANCELED,resultValue)

        setContentView(R.layout.config)

        sp=getSharedPreferences(WIDGET_PREF,MODE_PRIVATE)
        etFormat.setText(sp.getString(WIDGET_TIME_FORMAT+widgetID,"HH:mm:ss"))
        val cnt=sp.getInt(WIDGET_COUNT+widgetID,-1)
        if(cnt==-1)
            sp.edit().putInt(WIDGET_COUNT+widgetID,0)
    }

    fun onClick(v:View){
        sp.edit().putString(WIDGET_TIME_FORMAT+widgetID,etFormat.text.toString()).apply()
        MyWidget.updateWidget(this,AppWidgetManager.getInstance(this),widgetID,true)
        setResult(RESULT_OK,resultValue)
        finish()
    }
}