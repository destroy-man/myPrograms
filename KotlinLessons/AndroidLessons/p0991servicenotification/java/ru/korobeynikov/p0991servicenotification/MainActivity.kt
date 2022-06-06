package ru.korobeynikov.p0991servicenotification

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val FILE_NAME="filename"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filename=intent.getStringExtra(FILE_NAME)
        if(!TextUtils.isEmpty(filename))
            tv.text=filename
    }

    fun onClickStart(v:View){
        startService(Intent(this,MyService::class.java))
    }

    fun onClickStop(v:View){
        stopService(Intent(this,MyService::class.java))
    }
}