package ru.korobeynikov.p21androiddatabindingdoublesidedbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p21androiddatabindingdoublesidedbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.employee = Employee("Ivan Ivanov", false)
        binding.mainActivity = this
    }

    fun showEmployee() {
        Log.d(logTag, "${binding.employee}")
    }
}