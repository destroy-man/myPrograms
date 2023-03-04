package ru.korobeynikov.p0651alertdialogcustom

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0651alertdialogcustom.databinding.ActivityMainBinding
import ru.korobeynikov.p0651alertdialogcustom.databinding.DialogBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var view: ConstraintLayout
    private val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private lateinit var tvCount: TextView
    private var countTextView = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton(v: View) {
        val adb = AlertDialog.Builder(this)
        adb.setTitle("Custom dialog")
        val dialogBinding =
            DataBindingUtil.inflate<DialogBinding>(layoutInflater, R.layout.dialog, binding.conLayout, false)
        view = dialogBinding.root as ConstraintLayout
        adb.setView(view)
        tvCount = dialogBinding.tvCount
        val tvCountId = tvCount.id
        dialogBinding.tvTime.text = sdf.format(Date(System.currentTimeMillis()))
        if (v.id == R.id.btnAdd) {
            countTextView++
            addTextViews(tvCountId)
        } else {
            if (countTextView > 0)
                countTextView--
            addTextViews(tvCountId)
        }
        tvCount.text = getString(R.string.countTextView, countTextView)
        adb.create().show()
    }

    private fun addTextViews(tvCountId: Int) {
        if (countTextView > 0) {
            var lastId = -1
            for (i in 1..countTextView) {
                val tv = TextView(this)
                val lp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT)
                lp.startToStart = ConstraintSet.PARENT_ID
                lp.topToBottom = lastId
                if (lastId == -1)
                    lp.topToBottom = tvCountId
                lastId = ConstraintLayout.generateViewId()
                tv.id = lastId
                view.addView(tv, lp)
                tv.text = getString(R.string.textViewText, i)
            }
        }
    }
}