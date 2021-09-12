package com.korobeynikov.p0991servicenotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        val FILE_NAME="filename"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileName=intent.getStringExtra(FILE_NAME)
        if(!TextUtils.isEmpty(fileName))
            tv.text=fileName
    }

    fun onClickStart(v:View){
        startService(Intent(this,MyService::class.java))
    }

    fun onClickStop(v:View){
        stopService(Intent(this,MyService::class.java))
    }
}