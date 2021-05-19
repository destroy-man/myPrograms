package com.korobeynikov.p0261intentfilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.time.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time)

        val sdf=SimpleDateFormat("HH:mm:ss")
        val time=sdf.format(Date(System.currentTimeMillis()))
        tvTime.text=time
    }
}