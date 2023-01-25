package ru.korobeynikov.p0331sharedpreferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0331sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var etText: EditText
    private lateinit var sPref: SharedPreferences
    private val savedText = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        etText = binding.etText
        binding.view = this
        loadText()
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnSave -> saveText()
            R.id.btnLoad -> loadText()
        }
    }

    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putString(savedText, etText.text.toString())
        ed.apply()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadText() {
        sPref = getPreferences(MODE_PRIVATE)
        val text = sPref.getString(savedText, "")
        etText.setText(text)
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveText()
    }
}