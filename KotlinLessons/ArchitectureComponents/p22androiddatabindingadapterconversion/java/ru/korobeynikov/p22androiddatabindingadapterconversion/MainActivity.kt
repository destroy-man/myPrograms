package ru.korobeynikov.p22androiddatabindingadapterconversion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p22androiddatabindingadapterconversion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val avatarUrl = "https://lifehacker.ru/wp-content/uploads/2015/05/google-android-logo-green-black.jpg"
        binding.employee = Employee(1, "John Smith", "London", avatarUrl)
    }
}