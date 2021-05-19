package com.korobeynikov.p0291simpleactivityresult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.name.*

class NameActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.name)

        btnOK.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent=Intent()
        intent.putExtra("name",""+etName.text)
        setResult(RESULT_OK,intent)
        finish()
    }
}