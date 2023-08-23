package ru.korobeynikov.p04mockobjects.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.korobeynikov.p04mockobjects.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}