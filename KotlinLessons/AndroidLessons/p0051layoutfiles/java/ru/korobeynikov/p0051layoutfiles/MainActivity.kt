package ru.korobeynikov.p0051layoutfiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0051layoutfiles.databinding.MyscreenBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<MyscreenBinding>(this,R.layout.myscreen)
    }
}