package com.korobeynikov.p0121logandmess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private val TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"Найдем View-элементы")
        Log.d(TAG,"Присваиваем обработчик кнопкам")
        btnOk.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d(TAG,"По id определяем кнопку вызвавшую этот обработчик")
        when(v?.id){
            R.id.btnOk->{
                Log.d(TAG, "кнопка OK")
                tvOut.text = "Нажата кнопка OK"
                Toast.makeText(this,"Нажата кнопка OK",Toast.LENGTH_LONG).show()
            }
            R.id.btnCancel->{
                Log.d(TAG,"кнопка Cancel")
                tvOut.text = "Нажата кнопка Cancel"
                Toast.makeText(this,"Нажата кнопка Cancel",Toast.LENGTH_LONG).show()
            }
        }
    }
}