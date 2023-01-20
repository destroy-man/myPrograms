package ru.korobeynikov.p0281intentextras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0281intentextras.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityViewBinding>(this, R.layout.activity_view)
        val fName = intent.getStringExtra("fname")
        val lName = intent.getStringExtra("lname")
        binding.tvView.text = "Your name is: $fName $lName"
    }
}