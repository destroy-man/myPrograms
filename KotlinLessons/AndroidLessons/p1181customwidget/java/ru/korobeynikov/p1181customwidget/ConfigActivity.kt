package ru.korobeynikov.p1181customwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.config.*

class ConfigActivity : AppCompatActivity() {

    var widgetID=AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var resultValue:Intent
    val LOG_TAG="myLogs"

    companion object{
        val WIDGET_PREF="widget_pref"
        val WIDGET_TEXT="widget_text_"
        val WIDGET_COLOR="widget_color_"
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG_TAG,"onCreate config")
        val extras=intent.extras
        if(extras!=null)
            widgetID=extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
        if(widgetID==AppWidgetManager.INVALID_APPWIDGET_ID)finish()
        resultValue=Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID)
        setResult(RESULT_CANCELED,resultValue)

        setContentView(R.layout.config)
    }

    fun onClick(v:View){
        val selRBColor=rgColor.checkedRadioButtonId
        var color=Color.RED
        when(selRBColor){
            R.id.radioRed->color=Color.parseColor("#66ff0000")
            R.id.radioGreen->color=Color.parseColor("#6600ff00")
            R.id.radioBlue->color=Color.parseColor("#660000ff")
        }
        val sp=getSharedPreferences(WIDGET_PREF,MODE_PRIVATE)
        val editor=sp.edit()
        editor.putString(WIDGET_TEXT+widgetID,etText.text.toString())
        editor.putInt(WIDGET_COLOR+widgetID,color)
        editor.apply()
        val appWidgetManager=AppWidgetManager.getInstance(this)
        MyWidget.updateWidget(this,appWidgetManager,sp,widgetID)
        setResult(RESULT_OK,resultValue)
        Log.d(LOG_TAG,"finish config $widgetID")
        finish()
    }
}