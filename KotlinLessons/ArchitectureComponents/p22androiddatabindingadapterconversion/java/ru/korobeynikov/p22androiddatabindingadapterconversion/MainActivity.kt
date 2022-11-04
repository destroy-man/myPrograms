package ru.korobeynikov.p22androiddatabindingadapterconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p22androiddatabindingadapterconversion.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main, MyBindingComponent())
        binding.employee = Employee("Ivan Ivanov",
            "https://image.tmdb.org/t/p/w185//2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg")
    }
}