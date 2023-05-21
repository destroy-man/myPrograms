package ru.korobeynikov.p1181customwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1181customwidget.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {

    companion object {
        const val WIDGET_PREF = "widget_pref"
        const val WIDGET_TEXT = "widget_text_"
        const val WIDGET_COLOR = "widget_color_"
    }

    private var widgetID = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var resultValue: Intent
    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(Constants.LOG_TAG, "onCreate config")
        val extras = intent.extras
        if (extras != null)
            widgetID =
                extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish()
        resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
        setResult(RESULT_CANCELED, resultValue)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_config)
        binding.view = this
    }

    fun clickButton() {
        var color = Color.RED
        when (binding.rgColor.checkedRadioButtonId) {
            R.id.radioRed -> color = Color.parseColor("#66ff0000")
            R.id.radioGreen -> color = Color.parseColor("#6600ff00")
            R.id.radioBlue -> color = Color.parseColor("#660000ff")
        }
        val sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(WIDGET_TEXT + widgetID, binding.etText.text.toString())
        editor.putInt(WIDGET_COLOR + widgetID, color)
        editor.apply()
        val appWidgetManager = AppWidgetManager.getInstance(this)
        MyWidget.updateWidget(this, appWidgetManager, sp, widgetID)
        setResult(RESULT_OK, resultValue)
        Log.d(Constants.LOG_TAG, "finish config $widgetID")
        finish()
    }
}