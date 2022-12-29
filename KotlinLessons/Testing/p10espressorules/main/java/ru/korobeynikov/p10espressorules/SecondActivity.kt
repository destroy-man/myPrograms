package ru.korobeynikov.p10espressorules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10espressorules.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        binding.textSecond.text = "${intent.getIntExtra(Constants.EXTRA_ID, 0)}, " +
                "${intent.getStringExtra(Constants.EXTRA_NAME)}"
        binding.buttonSecond.setOnClickListener {
            buttonClick()
        }
    }

    private fun buttonClick() {
        val intent = Intent()
        intent.putExtra(Constants.EXTRA_RESULT, "Result")
        setResult(RESULT_OK, intent)
        finish()
    }
}