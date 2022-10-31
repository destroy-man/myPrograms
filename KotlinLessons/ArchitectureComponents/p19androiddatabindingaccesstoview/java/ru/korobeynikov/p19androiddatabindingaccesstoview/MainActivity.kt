package ru.korobeynikov.p19androiddatabindingaccesstoview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p19androiddatabindingaccesstoview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val map = HashMap<String, String>()
        map["key"] = "Play in videogames"
        map["key1"] = "Watch movie"
        map["key2"] = "Read books"
        val employee = Employee(1, "John Smith", "", 10000)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val rootView = binding.root
        rootView.setBackgroundColor(Color.MAGENTA)
        val textViewName = binding.name
        textViewName.setBackgroundColor(Color.YELLOW)
        binding.employee = employee
        binding.map = map
    }
}