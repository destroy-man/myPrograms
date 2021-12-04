package ru.korobeynikov.p0171dynamiclayout2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    val wrapContent=LinearLayout.LayoutParams.WRAP_CONTENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreate.setOnClickListener(this)
        btnClear.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCreate->{
                val lParams=LinearLayout.LayoutParams(wrapContent,wrapContent)
                var btnGravity=Gravity.LEFT
                when(rgGravity.checkedRadioButtonId){
                    R.id.rbLeft->btnGravity=Gravity.LEFT
                    R.id.rbCenter->btnGravity=Gravity.CENTER_HORIZONTAL
                    R.id.rbRight->btnGravity=Gravity.RIGHT
                }
                lParams.gravity=btnGravity
                val btnNew=Button(this)
                btnNew.text=etName.text.toString()
                llMain.addView(btnNew,lParams)
            }
            R.id.btnClear->{
                llMain.removeAllViews()
                Toast.makeText(this,"Удалено",Toast.LENGTH_SHORT).show()
            }
        }
    }
}