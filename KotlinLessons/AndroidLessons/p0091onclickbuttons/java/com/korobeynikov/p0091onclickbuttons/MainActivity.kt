package com.korobeynikov.p0091onclickbuttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oclBtnOk=View.OnClickListener{
            tvOut.text="Нажата кнопка OK"
        }
        btnOk.setOnClickListener(oclBtnOk)
        val oclBtnCancel=View.OnClickListener{
            tvOut.text="Нажата кнопка Cancel"
        }
        btnCancel.setOnClickListener(oclBtnCancel)
    }
}