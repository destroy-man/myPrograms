package ru.korobeynikov.p0281intentextras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent=Intent(this,ViewActivity::class.java)
        intent.putExtra("fname",etFName.text.toString())
        intent.putExtra("lname",etLName.text.toString())
        startActivity(intent)
    }
}