package ru.korobeynikov.p0561spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0561spinner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = arrayOf("one", "two", "three", "four", "five")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = binding.spinner
        spinner.adapter = adapter
        spinner.setSelection(2)
    }

    fun selectItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(baseContext, "Position = $position", Toast.LENGTH_SHORT).show()
    }
}