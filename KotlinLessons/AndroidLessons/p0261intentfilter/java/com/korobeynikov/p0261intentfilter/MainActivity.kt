package com.korobeynikov.p0261intentfilter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTime.setOnClickListener(this)
        btnDate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTime->{
                val intent=Intent("com.korobeynikov.intent.action.showtime")
                startActivity(intent)
            }
            R.id.btnDate->{
                val intent=Intent("com.korobeynikov.intent.action.showdate")
                startActivity(intent)
            }
        }
    }
}