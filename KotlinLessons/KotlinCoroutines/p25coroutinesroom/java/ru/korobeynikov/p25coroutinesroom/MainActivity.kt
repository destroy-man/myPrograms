package ru.korobeynikov.p25coroutinesroom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.korobeynikov.p25coroutinesroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        val db = App.getInstance().getDatabase()
        employeeDao = db.employeeDao()
        lifecycleScope.launch {
            employeeDao.getAll().asLiveData().observe(this@MainActivity) {
                for (employee in it)
                    log("$employee")
            }
        }
    }

    fun insertEmployees() {
        lifecycleScope.launch {
            employeeDao.insert(Employee(1, "John Smith", 10000))
            employeeDao.insert(Employee(2, "Ivan Ivanov", 20000))
        }
    }

    private fun log(text: String) = Log.d("myLogs", text)
}