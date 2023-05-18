package ru.korobeynikov.p1162mngtasks2

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1162mngtasks2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private lateinit var list: List<ActivityManager.AppTask>
    private lateinit var am: ActivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        title = "${resources.getString(R.string.app_name)} : $localClassName"
        am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
    }

    fun clickButton() {
        startActivity(Intent("mngtasks1_activity_c"))
    }

    fun infoClick() {
        list = am.appTasks
        for (task in list) {
            val taskInfo = task.taskInfo
            taskInfo.baseActivity?.let {
                if (it.flattenToShortString().startsWith("ru.korobeynikov.p116")) {
                    Log.d(logTag, "------------------")
                    Log.d(logTag, "Count: ${taskInfo.numActivities}")
                    Log.d(logTag, "Root: ${it.flattenToShortString()}")
                    Log.d(logTag, "Top: ${taskInfo.topActivity?.flattenToShortString()}")
                }
            }
        }
    }
}