package ru.korobeynikov.p0181dynamiclayout3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),SeekBar.OnSeekBarChangeListener {

    lateinit var lParams1: LinearLayout.LayoutParams
    lateinit var lParams2: LinearLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sbWeight.setOnSeekBarChangeListener(this)
        lParams1=btn1.layoutParams as LinearLayout.LayoutParams
        lParams2=btn2.layoutParams as LinearLayout.LayoutParams
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        val leftValue=progress
        var rightValue=0
        if(seekBar!=null)
            rightValue=seekBar.max-progress
        lParams1.weight=leftValue.toFloat()
        lParams2.weight=rightValue.toFloat()
        btn1.text=leftValue.toString()
        btn2.text=rightValue.toString()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}
}