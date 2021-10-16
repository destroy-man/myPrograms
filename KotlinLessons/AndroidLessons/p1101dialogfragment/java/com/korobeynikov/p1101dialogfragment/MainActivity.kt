package com.korobeynikov.p1101dialogfragment

import android.app.Dialog
import android.app.DialogFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var dlg1:DialogFragment
    lateinit var dlg2:DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dlg1=Dialog1()
        dlg2=Dialog2()
    }

    fun onClick(v:View){
        when(v.id){
            R.id.btnDlg1->dlg1.show(fragmentManager,"dlg1")
            R.id.btnDlg2->dlg2.show(fragmentManager,"dlg2")
        }
    }
}