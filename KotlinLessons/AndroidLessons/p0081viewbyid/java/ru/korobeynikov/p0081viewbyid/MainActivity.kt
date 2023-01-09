package ru.korobeynikov.p0081viewbyid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0081viewbyid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.myText.text = resources.getText(R.string.newButtonText)
        val myBtn = binding.myBtn
        myBtn.text = resources.getText(R.string.newCheckBoxText)
        myBtn.isEnabled = false
        binding.myChb.isChecked = true
    }
}