package ru.korobeynikov.p10espressointentstestrule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10espressointentstestrule.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivitySecondBinding>(this, R.layout.activity_second)
        binding.view = this
        textView = binding.text
        val id = intent.getIntExtra(Constants.EXTRA_ID, 0)
        val name = intent.getStringExtra(Constants.EXTRA_NAME)
        textView.text = getString(R.string.textview_second_activity, id, name)
    }

    fun buttonClick() {
        val intent = Intent()
        intent.putExtra(Constants.EXTRA_RESULT, "Result")
        setResult(RESULT_OK, intent)
        finish()
    }
}