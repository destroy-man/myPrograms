package ru.korobeynikov.p0601alertdialogsimple

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0601alertdialogsimple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun createAndShowDialog() {
        val adb = AlertDialog.Builder(this)
        adb.setTitle(R.string.exit)
        adb.setMessage(R.string.save_data)
        adb.setIcon(android.R.drawable.ic_dialog_info)
        adb.setPositiveButton(R.string.yes, myClickListener)
        adb.setNegativeButton(R.string.no, myClickListener)
        adb.setNeutralButton(R.string.cancel, myClickListener)
        adb.setCancelable(false)
        adb.create().show()
    }

    private val myClickListener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            Dialog.BUTTON_POSITIVE -> {
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
                finish()
            }
            Dialog.BUTTON_NEGATIVE -> finish()
        }
    }

    override fun onBackPressed() {
        createAndShowDialog()
    }
}