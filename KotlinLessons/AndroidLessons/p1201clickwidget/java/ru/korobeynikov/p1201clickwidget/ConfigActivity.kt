package ru.korobeynikov.p1201clickwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1201clickwidget.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {

    companion object {
        const val WIDGET_PREF = "widget_pref"
        const val WIDGET_TIME_FORMAT = "widget_time_format_"
        const val WIDGET_COUNT = "widget_count_"
    }

    private var widgetID = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var resultValue: Intent
    private lateinit var sp: SharedPreferences
    private lateinit var etFormat: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        if (extras != null)
            widgetID =
                extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish()
        resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
        setResult(RESULT_CANCELED, resultValue)
        val binding =
            DataBindingUtil.setContentView<ActivityConfigBinding>(this, R.layout.activity_config)
        binding.view = this
        sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE)
        etFormat = binding.etFormat
        etFormat.setText(sp.getString(WIDGET_TIME_FORMAT + widgetID, "HH:mm:ss"))
        val cnt = sp.getInt(WIDGET_COUNT + widgetID, -1)
        if (cnt == -1)
            sp.edit().putInt(WIDGET_COUNT + widgetID, 0).apply()
    }

    fun clickButton() {
        sp.edit().putString(WIDGET_TIME_FORMAT + widgetID, etFormat.text.toString()).apply()
        MyWidget.updateWidget(this, AppWidgetManager.getInstance(this), widgetID)
        setResult(RESULT_OK, resultValue)
        finish()
    }
}