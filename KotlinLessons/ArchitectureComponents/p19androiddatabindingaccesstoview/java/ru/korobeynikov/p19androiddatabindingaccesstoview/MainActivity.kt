package ru.korobeynikov.p19androiddatabindingaccesstoview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p19androiddatabindingaccesstoview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            employee = Employee(1, "John Smith", "London", 10000)
            department = Department(100, "IT")
            hobbies = mapOf(0 to "Books reading", 1 to "Movies watching", 2 to "Play in videogames")
        }
    }
}