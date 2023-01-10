package ru.korobeynikov.p0101listener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0101listener.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tvOut: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvOut = binding.tvOut
        binding.view = this
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnOk -> tvOut.text = resources.getText(R.string.clickOk)
            R.id.btnCancel -> tvOut.text = resources.getText(R.string.clickCancel)
        }
    }
}