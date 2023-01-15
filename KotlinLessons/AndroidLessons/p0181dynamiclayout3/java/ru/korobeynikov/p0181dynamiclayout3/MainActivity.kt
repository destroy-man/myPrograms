package ru.korobeynikov.p0181dynamiclayout3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0181dynamiclayout3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var sbWeight: SeekBar
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var clParams1: ConstraintLayout.LayoutParams
    private lateinit var clParams2: ConstraintLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        sbWeight = binding.sbWeight
        btn1 = binding.btn1
        btn2 = binding.btn2
        clParams1 = btn1.layoutParams as ConstraintLayout.LayoutParams
        clParams2 = btn2.layoutParams as ConstraintLayout.LayoutParams
        binding.view = this
    }

    fun progressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        val rightValue = seekBar.max - progress
        clParams1.horizontalWeight = progress.toFloat()
        clParams2.horizontalWeight = rightValue.toFloat()
        btn1.requestLayout()
        btn2.requestLayout()
        btn1.text = progress.toString()
        btn2.text = rightValue.toString()
    }
}