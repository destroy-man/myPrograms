package ru.korobeynikov.p0261intentfilter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.date.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityDate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date)

        val sdf=SimpleDateFormat("dd.MM.yyyy")
        val date=sdf.format(Date(System.currentTimeMillis()))
        tvDate.text=date
    }
}