package ru.korobeynikov.p21androiddatabindingobservablefields

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p21androiddatabindingobservablefields.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        binding.employee = Employee("John Smith", false)
    }

    fun changeEmployee(employee: Employee) = Log.d("myLogs", "employee = $employee")
}