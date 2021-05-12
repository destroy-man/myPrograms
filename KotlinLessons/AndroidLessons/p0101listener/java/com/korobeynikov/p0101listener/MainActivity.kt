package com.korobeynikov.p0101listener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oclBtn=View.OnClickListener{
            when(it.id){
                R.id.btnOk->tvOut.text="Нажата кнопка OK"
                R.id.btnCancel->tvOut.text="Нажата кнопка Cancel"
            }
        }
        btnOk.setOnClickListener(oclBtn)
        btnCancel.setOnClickListener(oclBtn)
    }
}