package ru.korobeynikov.p0961servicebackbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0961servicebackbroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val task1Code = 1
    val task2Code = 2
    val task3Code = 3

    companion object {
        const val LOG_TAG = "myLogs"
        const val STATUS_START = 100
        const val STATUS_FINISH = 200
        const val PARAM_TIME = "time"
        const val PARAM_TASK = "task"
        const val PARAM_RESULT = "result"
        const val PARAM_STATUS = "status"
        const val BROADCAST_ACTION = "ru.korobeynikov.p0961servicebackbroadcast"
    }

    lateinit var tvTask1: TextView
    lateinit var tvTask2: TextView
    lateinit var tvTask3: TextView
    private lateinit var br: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvTask1 = binding.tvTask1
        tvTask1.text = getText(R.string.task1)
        tvTask2 = binding.tvTask2
        tvTask2.text = getText(R.string.Task2)
        tvTask3 = binding.tvTask3
        tvTask3.text = getText(R.string.Task3)
        br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val task = intent?.getIntExtra(PARAM_TASK, 0)
                val status = intent?.getIntExtra(PARAM_STATUS, 0)
                Log.d(LOG_TAG, "onReceive: task = $task, status = $status")
                if (status == STATUS_START)
                    when (task) {
                        task1Code -> tvTask1.text = getText(R.string.task1_start)
                        task2Code -> tvTask2.text = getText(R.string.task2_start)
                        task3Code -> tvTask3.text = getText(R.string.task3_start)
                    }
                if (status == STATUS_FINISH) {
                    val result = intent.getIntExtra(PARAM_RESULT, 0)
                    when (task) {
                        task1Code -> tvTask1.text = getString(R.string.task1_finish, result)
                        task2Code -> tvTask2.text = getString(R.string.task2_finish, result)
                        task3Code -> tvTask3.text = getString(R.string.task3_finish, result)
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter(BROADCAST_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }

    fun clickStart() {
        launchService(7, task1Code)
        launchService(4, task2Code)
        launchService(6, task3Code)
    }

    private fun launchService(time: Int, task: Int) {
        val intent = Intent(this, MyService::class.java).putExtra(PARAM_TIME, time)
            .putExtra(PARAM_TASK, task)
        startService(intent)
    }
}