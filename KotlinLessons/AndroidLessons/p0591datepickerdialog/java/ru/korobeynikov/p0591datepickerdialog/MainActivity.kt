package ru.korobeynikov.p0591datepickerdialog

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0591datepickerdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var myYear = 2023
    private var myMonth = 2
    private var myDay = 15
    private lateinit var tvDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvDate = binding.tvDate
    }

    fun clickTextView() {
        DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
            myYear = year
            myMonth = monthOfYear + 1
            myDay = dayOfMonth
            tvDate.text = getString(R.string.date_text, myDay, myMonth, myYear)
        }, myYear, myMonth - 1, myDay).show()
    }
}