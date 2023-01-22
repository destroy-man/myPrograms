package ru.korobeynikov.p0301activityresult

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0301activityresult.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var typeResult = 0
    private lateinit var tvText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvText = binding.tvText
        binding.view = this
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnColor -> {
                val intent = Intent(this, ColorActivity::class.java)
                startActivityForResult.launch(intent)
                typeResult = 1
            }
            R.id.btnAlign -> {
                val intent = Intent(this, AlignActivity::class.java)
                startActivityForResult.launch(intent)
                typeResult = 2
            }
        }
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("myLogs", "typeResult = $typeResult, resultCode = ${result.resultCode}")
            if (result.resultCode == RESULT_OK)
                when (typeResult) {
                    1 -> {
                        val color = result.data?.getIntExtra("color", Color.WHITE)
                        if (color != null)
                            tvText.setTextColor(color)
                        else
                            tvText.setTextColor(Color.WHITE)
                    }
                    2 -> {
                        val align = result.data?.getIntExtra("alignment", Gravity.START)
                        if (align != null)
                            tvText.gravity = align
                        else
                            tvText.gravity = Gravity.START
                    }
                }
            else
                Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show()
        }
}