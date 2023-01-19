package ru.korobeynikov.p0261intentfilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0261intentfilter.databinding.ActivityDateBinding
import java.text.SimpleDateFormat
import java.util.*

class ActivityDateEx : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDateBinding>(this, R.layout.activity_date)
        val sdf = SimpleDateFormat("EEE, MMM d, yyyy")
        val date = sdf.format(Date(System.currentTimeMillis()))
        binding.tvDate.text = date
    }
}