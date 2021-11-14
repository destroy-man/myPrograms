package ru.korobeynikov.p0081viewbyid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTextView=myText
        myTextView.text="New text in TextView"
        myBtn.text="My button"
        myBtn.isEnabled=false
        myChb.isChecked=true
    }
}