package ru.korobeynikov.p0161dynamiclayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Создаем и настраиваем корневой ConstraintLayout
        val conLayout = ConstraintLayout(this)
        val conLayoutParams =
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT)
        setContentView(conLayout, conLayoutParams)
        //Создаем и настраиваем TextView
        val lpTextView = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        lpTextView.startToStart = ConstraintSet.PARENT_ID
        lpTextView.topToTop = ConstraintSet.PARENT_ID
        val tv = TextView(this)
        tv.id = R.id.tvId
        tv.text = resources.getText(R.string.textViewText)
        tv.layoutParams = lpTextView
        conLayout.addView(tv)
        //Создаем и настраиваем Button
        val lpButton = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        lpButton.startToStart = ConstraintSet.PARENT_ID
        lpButton.topToBottom = tv.id
        val btn = Button(this)
        btn.id = R.id.buttonId
        btn.text = resources.getText(R.string.button1Text)
        conLayout.addView(btn, lpButton)
        //Создаем и настраиваем Button1
        val lpButton1 = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        lpButton1.startToStart = ConstraintSet.PARENT_ID
        lpButton1.topToBottom = btn.id
        lpButton1.leftMargin = 50
        val btn1 = Button(this)
        btn1.id = R.id.button1Id
        btn1.text = resources.getText(R.string.button1Text)
        conLayout.addView(btn1, lpButton1)
        //Создаем и настраиваем Button2
        val lpButton2 = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        lpButton2.endToEnd = ConstraintSet.PARENT_ID
        lpButton2.topToBottom = btn1.id
        val btn2 = Button(this)
        btn2.text = resources.getText(R.string.button2Text)
        conLayout.addView(btn2, lpButton2)
    }
}