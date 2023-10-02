package ru.korobeynikov.p05memoryprofilerallocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p05memoryprofilerallocation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<MyObject>(50000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun create() {
        for (i in 0 until 50000)
            list.add(MyObject())
    }

    fun release() = list.clear()
}