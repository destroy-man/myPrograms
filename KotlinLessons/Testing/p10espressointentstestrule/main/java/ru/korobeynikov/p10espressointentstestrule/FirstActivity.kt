package ru.korobeynikov.p10espressointentstestrule

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10espressointentstestrule.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityFirstBinding>(this, R.layout.activity_first)
        binding.view = this
        textView = binding.text
    }

    fun buttonClick() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(Constants.EXTRA_ID, 1)
        intent.putExtra(Constants.EXTRA_NAME, "Name 1")
        startForResult.launch(intent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            textView.text =
                if (result.resultCode == RESULT_OK)
                    result.data?.getStringExtra(Constants.EXTRA_RESULT)
                else "Cancel"
        }
}