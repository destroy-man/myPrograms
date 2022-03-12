package ru.korobeynikov.p0591datepickerdialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    protected override fun onCreateDialog(id: Int): Dialog {
        if(id==DIALOG_DATE){
            val dpd=DatePickerDialog(this,myCallBack,myYear,myMonth,myDay)
            return dpd
        }
        return super.onCreateDialog(id)
    }

    val myCallBack=DatePickerDialog.OnDateSetListener{view,year,monthOfYear,dayOfMonth->
        myYear=year
        myMonth=monthOfYear
        myDay=dayOfMonth
        tvDate.text="Today is $myDay/$myMonth/$myYear"
    }
}