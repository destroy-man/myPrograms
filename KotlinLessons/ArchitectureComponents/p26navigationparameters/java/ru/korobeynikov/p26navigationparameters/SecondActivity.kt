package ru.korobeynikov.p26navigationparameters

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p26navigationparameters.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second)
        Log.d(MainActivity.TAG, "${intent.data}")
    }
}