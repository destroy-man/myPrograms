package ru.korobeynikov.p0661alertdialogoperations

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0661alertdialogoperations.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    private fun method1() {
        dialog?.dismiss()
        dialog = null
    }

    private fun method2() {
        runOnUiThread {
            createDialog()
            dialog?.show()
        }
    }

    fun clickButton() {
        if (dialog == null)
            createDialog()
        dialog?.show()
        val executor = MyExecutor()
        executor.execute {
            TimeUnit.SECONDS.sleep(2)
            method1()
        }
        executor.execute {
            TimeUnit.SECONDS.sleep(4)
            method2()
        }
    }

    private fun createDialog() {
        Log.d(logTag, "Create")
        val adb = AlertDialog.Builder(this)
        adb.setTitle("Title")
        adb.setMessage("Message")
        adb.setPositiveButton("OK", null)
        dialog = adb.create()
        dialog?.setOnShowListener {
            Log.d(logTag, "Show")
        }
        dialog?.setOnCancelListener {
            Log.d(logTag, "Cancel")
        }
        dialog?.setOnDismissListener {
            Log.d(logTag, "Dismiss")
        }
    }
}