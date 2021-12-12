package ru.korobeynikov.p0271getintentaction

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.info.*
import java.text.SimpleDateFormat
import java.util.*

class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)

        val action=intent.action
        var format=""
        var textInfo=""
        if(action=="ru.korobeynikov.intent.action.showtime"){
            format="HH:mm:ss"
            textInfo="Time: "
        }
        else if(action=="ru.korobeynikov.intent.action.showdate"){
            format="dd.MM.yyyy"
            textInfo="Date: "
        }
        val sdf=SimpleDateFormat(format)
        val datetime=sdf.format(Date(System.currentTimeMillis()))
        val tvDate=tvInfo
        tvDate.text=textInfo+datetime
    }
}