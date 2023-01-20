package ru.korobeynikov.p0281intentextras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0281intentextras.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var etFName: EditText
    private lateinit var etLName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        etFName = binding.etFName
        etLName = binding.etLName
        binding.view = this
    }

    fun clickButton() {
        val intent = Intent(this, ViewActivity::class.java)
        intent.putExtra("fname", etFName.text.toString())
        intent.putExtra("lname", etLName.text.toString())
        startActivity(intent)
    }
}