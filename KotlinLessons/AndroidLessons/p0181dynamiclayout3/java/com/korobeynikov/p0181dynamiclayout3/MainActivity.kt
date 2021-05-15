package com.korobeynikov.p0181dynamiclayout3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),SeekBar.OnSeekBarChangeListener {

    lateinit var lParams1:LinearLayout.LayoutParams
    lateinit var lParams2:LinearLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sbWeight.setOnSeekBarChangeListener(this)
        lParams1=btn1.layoutParams as LinearLayout.LayoutParams
        lParams2=btn2.layoutParams as LinearLayout.LayoutParams
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val leftValue=progress
        val rightValue=seekBar?.max?.minus(progress)
        lParams1.weight=leftValue.toFloat()
        lParams2.weight=rightValue!!.toFloat()
        btn1.text=""+leftValue
        btn2.text=""+rightValue
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}