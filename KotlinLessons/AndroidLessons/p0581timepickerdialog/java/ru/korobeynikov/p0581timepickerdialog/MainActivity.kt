package ru.korobeynikov.p0581timepickerdialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val DIALOG_TIME=1
    var myHour=14
    var myMinute=35

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(view:View){
        showDialog(DIALOG_TIME)
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        if(id==DIALOG_TIME){
            val tpd=TimePickerDialog(this,myCallBack,myHour,myMinute,true)
            return tpd
        }
        return super.onCreateDialog(id)
    }

    val myCallBack=TimePickerDialog.OnTimeSetListener{view,hourOfDay,minute->
        myHour=hourOfDay
        myMinute=minute
        tvTime.text="Time is $myHour hours $myMinute minutes"
    }
}