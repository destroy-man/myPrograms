package ru.korobeynikov.p0261intentfilter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
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
        var intent:Intent
        when(v?.id){
            R.id.btnTime->{
                intent=Intent("ru.korobeynikov.intent.action.showtime")
                startActivity(intent)
            }
            R.id.btnDate->{
                intent=Intent("ru.korobeynikov.intent.action.showdate")
                startActivity(intent)
            }
        }
    }
}