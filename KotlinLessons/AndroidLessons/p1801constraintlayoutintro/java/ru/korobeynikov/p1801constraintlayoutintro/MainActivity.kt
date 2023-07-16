package ru.korobeynikov.p1801constraintlayoutintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1801constraintlayoutintro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}