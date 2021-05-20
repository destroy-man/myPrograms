package com.korobeynikov.p0301activityresult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.align.*

class AlignActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.align)

        btnLeft.setOnClickListener(this)
        btnCenter.setOnClickListener(this)
        btnRight.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent=Intent()
        when(v?.id){
            R.id.btnLeft->intent.putExtra("alignment",Gravity.LEFT)
            R.id.btnCenter->intent.putExtra("alignment",Gravity.CENTER)
            R.id.btnRight->intent.putExtra("alignment",Gravity.RIGHT)
        }
        setResult(RESULT_OK,intent)
        finish()
    }
}