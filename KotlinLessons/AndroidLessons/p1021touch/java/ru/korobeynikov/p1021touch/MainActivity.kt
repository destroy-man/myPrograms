package ru.korobeynikov.p1021touch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity(),View.OnTouchListener {

    lateinit var tv:TextView
    var x:Float?=0f
    var y:Float?=0f
    lateinit var sDown: String
    lateinit var sMove: String
    lateinit var sUp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv= TextView(this)
        tv.setOnTouchListener(this)
        setContentView(tv)
    }

    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
        x=event?.x
        y=event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                sDown="Down: $x,$y"
                sMove=""
                sUp=""
            }
            MotionEvent.ACTION_MOVE->sMove="Move: $x,$y"
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL->{
                sMove=""
                sUp="Up: $x,$y"
            }
        }
        tv.text="$sDown\n$sMove\n$sUp"
        return true
    }
}