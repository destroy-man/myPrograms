package ru.korobeynikov.p10espressorules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10espressorules.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_first)
        binding.view = this
    }

    fun buttonClick() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(Constants.EXTRA_ID, 1)
        intent.putExtra(Constants.EXTRA_NAME, "Name 1")
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                binding.textFirst.text = data?.getStringExtra(Constants.EXTRA_RESULT)
            else
                binding.textFirst.text = "Cancel"
        }
    }
}