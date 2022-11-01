package ru.korobeynikov.p20androiddatabindingprocessingevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p20androiddatabindingprocessingevents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val employee = Employee(1, "Ivan Ivanov", "Moscow", 10000)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.employee = employee
        val myHandler = MyHandler()
        binding.handler = myHandler
        binding.context = binding.root.context
    }
}