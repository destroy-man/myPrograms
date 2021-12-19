package ru.korobeynikov.p0291simpleactivityresult

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnName.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent=Intent(this,NameActivity::class.java)
        startActivityForResult(intent,1)
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data==null)return
        val name=data.getStringExtra("name")
        tvName.text="Your name is "+name
    }
}