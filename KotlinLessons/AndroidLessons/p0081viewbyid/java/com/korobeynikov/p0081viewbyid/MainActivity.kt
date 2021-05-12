package com.korobeynikov.p0081viewbyid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTextView=findViewById<TextView>(R.id.myText)
        myTextView.setText("New text in TextView")
        val myBtn=findViewById<Button>(R.id.myBtn)
        myBtn.setText("My button")
        myBtn.isEnabled=false
        val myChb=findViewById<CheckBox>(R.id.myChb)
        myChb.isChecked=true
    }
}