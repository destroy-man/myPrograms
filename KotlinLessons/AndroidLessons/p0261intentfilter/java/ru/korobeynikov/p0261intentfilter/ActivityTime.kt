package ru.korobeynikov.p0261intentfilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0261intentfilter.databinding.ActivityTimeBinding
import java.text.SimpleDateFormat
import java.util.*

class ActivityTime : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityTimeBinding>(this, R.layout.activity_time)
        val sdf = SimpleDateFormat("HH:mm:ss")
        val time = sdf.format(Date(System.currentTimeMillis()))
        binding.tvTime.text = time
    }
}