package com.korobeynikov.p0591datepickerdialog

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val DIALOG_DATE=1
    var myYear=2011
    var myMonth=2
    var myDay=3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(view:View){
        showDialog(DIALOG_DATE)
    }

    protected override fun onCreateDialog(id:Int):Dialog{
        if(id==DIALOG_DATE){
            val tpd=DatePickerDialog(this,myCallBack,myYear,myMonth,myDay)
            return tpd
        }
        return super.onCreateDialog(id)
    }

    val myCallBack=DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
        myYear=year
        myMonth=month
        myDay=dayOfMonth
        tvDate.text="Today is "+myDay+"/"+myMonth+"/"+myYear
    }
}