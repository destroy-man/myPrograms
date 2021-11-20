package ru.korobeynikov.p0101listener

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oclBtn=View.OnClickListener{v->
            when(v.id){
                R.id.btnOk->tvOut.text="Нажата кнопка OK"
                R.id.btnCancel->tvOut.text="Нажата кнопка Cancel"
            }
        }
        btnOk.setOnClickListener(oclBtn)
        btnCancel.setOnClickListener(oclBtn)
    }
}