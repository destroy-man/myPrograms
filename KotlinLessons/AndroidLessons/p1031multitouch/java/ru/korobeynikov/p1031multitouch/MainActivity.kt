package ru.korobeynikov.p1031multitouch

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val sb = StringBuilder()
    private var upPI = 0
    private var downPI = 0
    private var inTouch = false
    private lateinit var result: StringBuilder

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(TextView(this)) {
            textSize = 30f
            setOnTouchListener { v, event ->
                val pointerIndex = event.actionIndex
                val pointerCount = event.pointerCount
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        inTouch = true
                        downPI = pointerIndex
                    }
                    MotionEvent.ACTION_POINTER_DOWN -> downPI = pointerIndex
                    MotionEvent.ACTION_UP -> {
                        inTouch = false
                        sb.clear()
                        upPI = pointerIndex
                    }
                    MotionEvent.ACTION_POINTER_UP -> upPI = pointerIndex
                    MotionEvent.ACTION_MOVE -> {
                        sb.clear()
                        for (i in 0 until 10) {
                            sb.append("Index = $i")
                            if (i < pointerCount) {
                                sb.append(", ID = ${event.getPointerId(i)}")
                                sb.append(", X = ${event.getX(i)}")
                                sb.append(", Y = ${event.getY(i)}")
                            } else {
                                sb.append(", ID = ")
                                sb.append(", X = ")
                                sb.append(", Y = ")
                            }
                            sb.append("\r\n")
                        }
                    }
                }
                result = StringBuilder("down: $downPI\nup: $upPI\n")
                if (inTouch)
                    result.append("pointerCount = $pointerCount\n$sb")
                text = result.toString()
                true
            }
            setContentView(this)
        }
    }
}