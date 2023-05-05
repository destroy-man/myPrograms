package ru.korobeynikov.p1001serviceautoload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1001serviceautoload.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}