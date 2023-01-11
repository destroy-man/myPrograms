package ru.korobeynikov.p0121logandmess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0121logandmess.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tvOut: TextView

    companion object {
        private const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Log.d(TAG, "найдем TextView")
        tvOut = binding.tvOut
        Log.d(TAG, "присваиваем view текущую activity")
        binding.view = this
    }

    fun clickButton(v: View) {
        Log.d(TAG, "по id определяем кнопку, вызвавшую этот метод")
        when (v.id) {
            R.id.btnOk -> {
                Log.d(TAG, "кнопка OK")
                tvOut.text = resources.getText(R.string.clickOk)
                Toast.makeText(this, resources.getText(R.string.clickOk), Toast.LENGTH_LONG).show()
            }
            R.id.btnCancel -> {
                Log.d(TAG, "кнопка Cancel")
                tvOut.text = resources.getText(R.string.clickCancel)
                Toast.makeText(this, resources.getText(R.string.clickCancel), Toast.LENGTH_LONG).show()
            }
        }
    }
}