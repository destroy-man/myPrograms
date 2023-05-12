package ru.korobeynikov.p1101dialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import ru.korobeynikov.p1101dialogfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    private lateinit var dlg1: DialogFragment
    private lateinit var dlg2: DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        dlg1 = Dialog1()
        dlg2 = Dialog2()
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnDlg1 -> dlg1.show(supportFragmentManager, "dlg1")
            R.id.btnDlg2 -> dlg2.show(supportFragmentManager, "dlg2")
        }
    }
}