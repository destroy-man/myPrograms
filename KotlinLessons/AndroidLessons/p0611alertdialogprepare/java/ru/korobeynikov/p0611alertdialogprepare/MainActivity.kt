package ru.korobeynikov.p0611alertdialogprepare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0611alertdialogprepare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton() {
        Log.d("myLogs", "onCreateDialog")
        DialogAlert().show(supportFragmentManager, "currentTime")
    }
}