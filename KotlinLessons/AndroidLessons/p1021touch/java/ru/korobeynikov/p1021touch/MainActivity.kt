package ru.korobeynikov.p1021touch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var x = 0f
    private var y = 0f
    private lateinit var tv: TextView
    private lateinit var sDown: String
    private lateinit var sMove: String
    private lateinit var sUp: String

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv = TextView(this)
        tv.setOnTouchListener { v, event ->
            x = event.x
            y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    sDown = "Down: $x,$y"
                    sMove = ""
                    sUp = ""
                }
                MotionEvent.ACTION_MOVE -> sMove = "Move: $x,$y"
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    sMove = ""
                    sUp = "Up: $x,$y"
                }
            }
            tv.text = getString(R.string.text_moves, sDown, sMove, sUp)
            true
        }
        setContentView(tv)
    }
}