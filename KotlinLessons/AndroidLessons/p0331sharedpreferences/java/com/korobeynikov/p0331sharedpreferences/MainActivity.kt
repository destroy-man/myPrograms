package com.korobeynikov.p0331sharedpreferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var sPref:SharedPreferences
    val SAVED_TEXT="saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSave.setOnClickListener(this)
        btnLoad.setOnClickListener(this)
        loadText()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSave->saveText()
            R.id.btnLoad->loadText()
        }
    }

    fun saveText(){
        sPref=getPreferences(MODE_PRIVATE)
        val ed=sPref.edit()
        ed.putString(SAVED_TEXT,""+etText.text)
        ed.commit()
        Toast.makeText(this,"Text saved",Toast.LENGTH_SHORT).show()
    }

    fun loadText(){
        sPref=getPreferences(MODE_PRIVATE)
        val savedText=sPref.getString(SAVED_TEXT,"")
        etText.setText(savedText)
        Toast.makeText(this,"Text loaded",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveText()
    }
}