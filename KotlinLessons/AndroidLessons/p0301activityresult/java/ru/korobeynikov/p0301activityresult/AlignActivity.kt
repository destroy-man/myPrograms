package ru.korobeynikov.p0301activityresult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0301activityresult.databinding.ActivityAlignBinding

class AlignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityAlignBinding>(this,
            R.layout.activity_align)
        binding.view = this
    }

    fun clickButton(v: View) {
        val intent = Intent()
        when (v.id) {
            R.id.btnLeft -> intent.putExtra("alignment", Gravity.START)
            R.id.btnCenter -> intent.putExtra("alignment", Gravity.CENTER)
            R.id.btnRight -> intent.putExtra("alignment", Gravity.END)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}