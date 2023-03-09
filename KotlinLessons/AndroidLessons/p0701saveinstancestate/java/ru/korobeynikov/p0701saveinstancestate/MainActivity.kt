package ru.korobeynikov.p0701saveinstancestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0701saveinstancestate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        Log.d(logTag, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(logTag, "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d(logTag, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(logTag, "onRestart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        cnt = savedInstanceState.getInt("count")
        Log.d(logTag, "onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.d(logTag, "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", cnt)
        Log.d(logTag, "onSaveInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(logTag, "onStop")
    }

    fun clickButton() {
        Toast.makeText(this, "Count = ${++cnt}", Toast.LENGTH_SHORT).show()
    }
}