package ru.korobeynikov.p0691parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0691parcelable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(MyObject::class.simpleName, MyObject("text", 1))
        Log.d(LOG_TAG, "startActivity")
        startActivity(intent)
    }
}