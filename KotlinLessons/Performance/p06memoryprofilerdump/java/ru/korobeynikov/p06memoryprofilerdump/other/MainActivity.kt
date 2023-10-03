package ru.korobeynikov.p06memoryprofilerdump.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p06memoryprofilerdump.R
import ru.korobeynikov.p06memoryprofilerdump.databinding.ActivityMainBinding
import ru.korobeynikov.p06memoryprofilerdump.performance.*

class MainActivity : AppCompatActivity() {

    private lateinit var a: A
    private lateinit var c: C

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun create() {
        val e = E()
        a = A()
        a.b = B()
        a.b.e = e
        c = C()
        c.d = D()
        c.d.e = e
    }
}