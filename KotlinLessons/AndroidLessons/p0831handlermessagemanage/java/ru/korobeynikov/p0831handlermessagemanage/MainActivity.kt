package ru.korobeynikov.p0831handlermessagemanage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0831handlermessagemanage.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private lateinit var executor: MyExecutor

    private fun getStatusFromExecutor() {
        Log.d(logTag, "status = ${executor.status}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        executor = MyExecutor()
        sendMessages()
    }

    private fun sendMessages() {
        Log.d(logTag, "send messages")
        executor.execute {
            TimeUnit.MILLISECONDS.sleep(1000)
            executor.status = 1
            getStatusFromExecutor()
        }
        executor.execute {
            TimeUnit.MILLISECONDS.sleep(3000)
            executor.status = 3
            getStatusFromExecutor()
        }
        executor.execute {
            TimeUnit.MILLISECONDS.sleep(5000)
            executor.status = 5
            getStatusFromExecutor()
        }
        executor.execute {
            TimeUnit.MILLISECONDS.sleep(7000)
            executor.status = 7
            getStatusFromExecutor()
        }
    }
}