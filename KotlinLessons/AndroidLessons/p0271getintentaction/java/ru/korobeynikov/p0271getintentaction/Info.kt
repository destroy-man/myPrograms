package ru.korobeynikov.p0271getintentaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0271getintentaction.databinding.ActivityInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityInfoBinding>(this, R.layout.activity_info)
        val action = intent.action
        var format = ""
        var textInfo = ""
        if (action == "ru.korobeynikov.intent.action.showtime") {
            format = "HH:mm:ss"
            textInfo = "Time: "
        } else if (action == "ru.korobeynikov.intent.action.showdate") {
            format = "dd.MM.yyyy"
            textInfo = "Date: "
        }
        val sdf = SimpleDateFormat(format)
        val datetime = sdf.format(Date(System.currentTimeMillis()))
        binding.tvInfo.text = "$textInfo $datetime"
    }
}