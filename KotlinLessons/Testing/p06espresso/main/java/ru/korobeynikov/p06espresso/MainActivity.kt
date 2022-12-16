package ru.korobeynikov.p06espresso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p06espresso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
    }

    fun buttonClick() {
        var text = binding.editText.text.toString()
        if (TextUtils.isEmpty(text))
            text = getString(R.string.empty_text)
        binding.textView.text = text
    }
}