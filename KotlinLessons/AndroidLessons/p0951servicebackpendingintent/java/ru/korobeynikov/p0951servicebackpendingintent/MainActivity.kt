package ru.korobeynikov.p0951servicebackpendingintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0951servicebackpendingintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val task1Code = 1
    private val task2Code = 2
    private val task3Code = 3

    companion object {
        const val LOG_TAG = "myLogs"
        const val STATUS_START = 100
        const val STATUS_FINISH = 200
        const val PARAM_TIME = "time"
        const val PARAM_PINTENT = "pendingIntent"
        const val PARAM_RESULT = "result"
    }

    private lateinit var tvTask1: TextView
    private lateinit var tvTask2: TextView
    private lateinit var tvTask3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvTask1 = binding.tvTask1
        tvTask1.text = getText(R.string.task1)
        tvTask2 = binding.tvTask2
        tvTask2.text = getText(R.string.task2)
        tvTask3 = binding.tvTask3
        tvTask3.text = getText(R.string.task3)
    }

    fun clickStart() {
        launchService(task1Code, 7)
        launchService(task2Code, 4)
        launchService(task3Code, 6)
    }

    private fun launchService(serviceCode: Int, time: Int) {
        val pi = createPendingResult(serviceCode, Intent(), 0)
        val intent = Intent(this, MyService::class.java).putExtra(PARAM_TIME, time)
            .putExtra(PARAM_PINTENT, pi)
        startService(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_TAG, "requestCode = $requestCode, resultCode = $resultCode")
        if (resultCode == STATUS_START)
            when (requestCode) {
                task1Code -> tvTask1.text = getText(R.string.task1Start)
                task2Code -> tvTask2.text = getText(R.string.task2Start)
                task3Code -> tvTask3.text = getText(R.string.task3Start)
            }
        if (resultCode == STATUS_FINISH) {
            val result = data?.getIntExtra(PARAM_RESULT, 0)
            when (requestCode) {
                task1Code -> tvTask1.text = getString(R.string.task1Finish, result)
                task2Code -> tvTask2.text = getString(R.string.task2Finish, result)
                task3Code -> tvTask3.text = getString(R.string.task3Finish, result)
            }
        }
    }
}