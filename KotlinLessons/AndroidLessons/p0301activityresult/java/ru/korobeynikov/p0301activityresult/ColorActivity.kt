package ru.korobeynikov.p0301activityresult

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0301activityresult.databinding.ActivityColorBinding

class ColorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityColorBinding>(this,
            R.layout.activity_color)
        binding.view = this
    }

    fun clickButton(v: View) {
        val intent = Intent()
        when (v.id) {
            R.id.btnRed -> intent.putExtra("color", Color.RED)
            R.id.btnGreen -> intent.putExtra("color", Color.GREEN)
            R.id.btnBlue -> intent.putExtra("color", Color.BLUE)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}