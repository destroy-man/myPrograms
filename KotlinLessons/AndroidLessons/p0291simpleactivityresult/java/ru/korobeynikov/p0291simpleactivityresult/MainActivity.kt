package ru.korobeynikov.p0291simpleactivityresult

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0291simpleactivityresult.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tvName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvName = binding.tvName
        binding.view = this
    }

    fun clickButton() {
        startForResult.launch(Intent(this, NameActivity::class.java))
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data == null)
                    return@registerForActivityResult
                val name = result.data?.getStringExtra("name")
                tvName.text = "${resources.getText(R.string.textViewYourName)} $name"
            }
        }
}