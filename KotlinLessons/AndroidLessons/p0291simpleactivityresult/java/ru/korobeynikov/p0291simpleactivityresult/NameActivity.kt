package ru.korobeynikov.p0291simpleactivityresult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0291simpleactivityresult.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {

    private lateinit var etName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityNameBinding>(this, R.layout.activity_name)
        etName = binding.etName
        binding.view = this
    }

    fun clickButton() {
        val intent = Intent()
        intent.putExtra("name", etName.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}