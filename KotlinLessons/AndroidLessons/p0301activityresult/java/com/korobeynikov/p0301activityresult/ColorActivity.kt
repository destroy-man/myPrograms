package com.korobeynikov.p0301activityresult

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.color.*

class ColorActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color)

        btnRed.setOnClickListener(this)
        btnGreen.setOnClickListener(this)
        btnBlue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent=Intent()
        when(v?.id){
            R.id.btnRed->intent.putExtra("color",Color.RED)
            R.id.btnGreen->intent.putExtra("color",Color.GREEN)
            R.id.btnBlue->intent.putExtra("color",Color.BLUE)
        }
        setResult(RESULT_OK,intent)
        finish()
    }
}