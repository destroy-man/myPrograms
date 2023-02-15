package ru.korobeynikov.p0581timepickerdialog

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0581timepickerdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var myHour = 14
    private var myMinute = 35
    private lateinit var tvTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvTime = binding.tvTime
    }

    fun clickTextView() {
        TimePickerDialog(this, { view, hourOfDay, minute ->
            myHour = hourOfDay
            myMinute = minute
            tvTime.text = getString(R.string.time_text, myHour, myMinute)
        }, myHour, myMinute, true).show()
    }
}