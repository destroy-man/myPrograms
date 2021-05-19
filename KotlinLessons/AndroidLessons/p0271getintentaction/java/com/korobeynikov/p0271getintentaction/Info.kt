package com.korobeynikov.p0271getintentaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.info.*
import java.text.SimpleDateFormat
import java.util.*

class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.info)
        val intent=getIntent()
        val action=intent.action
        var format=""
        var textInfo=""
        if(action.equals("com.korobeynikov.intent.action.showtime")){
            format="HH:mm:ss"
            textInfo="Time: "
        }
        else if(action.equals("com.korobeynikov.intent.action.showdate")){
            format="dd.MM.yyyy"
            textInfo="Date: "
        }
        val sdf=SimpleDateFormat(format)
        val datetime=sdf.format(Date(System.currentTimeMillis()))
        tvInfo.text=textInfo+datetime
    }
}