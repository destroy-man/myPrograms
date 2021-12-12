package ru.korobeynikov.p0281intentextras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.view.*

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view)

        val fName=intent.getStringExtra("fname")
        val lName=intent.getStringExtra("lname")
        tvView.text="Your name is: "+fName+" "+lName
    }
}