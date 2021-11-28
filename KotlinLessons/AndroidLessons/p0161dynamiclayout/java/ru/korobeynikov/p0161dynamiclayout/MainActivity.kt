package ru.korobeynikov.p0161dynamiclayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linLayout=LinearLayout(this)
        linLayout.orientation=LinearLayout.VERTICAL
        val linLayoutParam=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(linLayout,linLayoutParam)
        val lpView=LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        val tv=TextView(this)
        tv.text="TextView"
        tv.layoutParams=lpView
        linLayout.addView(tv)
        val btn=Button(this)
        btn.text="Button"
        linLayout.addView(btn,lpView)
        val leftMarginParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        leftMarginParams.leftMargin=50
        val btn1=Button(this)
        btn1.text="Button1"
        linLayout.addView(btn1,leftMarginParams)
        val rightGravityParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        rightGravityParams.gravity=Gravity.RIGHT
        val btn2=Button(this)
        btn2.text="Button2"
        linLayout.addView(btn2,rightGravityParams)
    }
}