package ru.korobeynikov.p06espresso

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p06espresso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        textView = binding.textView
        editText = binding.editText
    }

    fun clickButton() {
        var text = editText.text.toString()
        if (text.isEmpty())
            text = getString(R.string.empty_text)
        textView.text = text
    }
}