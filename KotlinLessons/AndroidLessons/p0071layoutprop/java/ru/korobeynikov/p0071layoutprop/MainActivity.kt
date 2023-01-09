package ru.korobeynikov.p0071layoutprop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0071layoutprop.databinding.MarginlayoutBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MarginlayoutBinding>(this, R.layout.marginlayout)
    }
}