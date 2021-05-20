package com.korobeynikov.p0311simpleintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWeb.setOnClickListener(this)
        btnMap.setOnClickListener(this)
        btnCall.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnWeb->{
                val intent=Intent(Intent.ACTION_VIEW,Uri.parse("http://developer.android.com"))
                startActivity(intent)
            }
            R.id.btnMap->{
                val intent=Intent()
                intent.setAction(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("geo:55.754283,37.62002"))
                startActivity(intent)
            }
            R.id.btnCall->{
                val intent=Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:12345"))
                startActivity(intent)
            }
        }
    }
}