package ru.korobeynikov.p0671progressdialog

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0671progressdialog.databinding.ActivityMainBinding
import ru.korobeynikov.p0671progressdialog.databinding.DialogDefaultBinding
import ru.korobeynikov.p0671progressdialog.databinding.DialogHorizBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var pb: ProgressBar
    private lateinit var executor: MyExecutor
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton(v: View) {
        val adb = AlertDialog.Builder(this)
        adb.setTitle("Title")
        adb.setMessage("Message")
        when (v.id) {
            R.id.btnDefault -> {
                adb.setPositiveButton("OK", null)
                val dialogBinding = DataBindingUtil.inflate<DialogDefaultBinding>(layoutInflater,
                    R.layout.dialog_default, binding.conLayout, false)
                adb.setView(dialogBinding.root)
                adb.create().show()
            }
            R.id.btnHoriz -> {
                val dialogBinding = DataBindingUtil.inflate<DialogHorizBinding>(layoutInflater,
                    R.layout.dialog_horiz, binding.conLayout, false)
                pb = dialogBinding.progressBar
                pb.max = 2148
                pb.isIndeterminate = true
                adb.setView(dialogBinding.root)
                val dialog = adb.create()
                executor = MyExecutor()
                executor.execute {
                    TimeUnit.SECONDS.sleep(2)
                    pb.isIndeterminate = false
                    while (pb.progress < pb.max) {
                        pb.progress += 50
                        val percent = pb.progress.toFloat() / pb.max * 100
                        runOnUiThread {
                            dialogBinding.textPercent.text = getString(R.string.percent, percent.toInt())
                            dialogBinding.textProgress.text =
                                getString(R.string.progress, pb.progress, pb.max)
                        }
                        TimeUnit.MILLISECONDS.sleep(100)
                    }
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }
}