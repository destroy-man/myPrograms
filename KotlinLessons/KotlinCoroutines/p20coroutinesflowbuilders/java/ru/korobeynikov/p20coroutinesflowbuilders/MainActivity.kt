package ru.korobeynikov.p20coroutinesflowbuilders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.korobeynikov.p20coroutinesflowbuilders.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        CoroutineScope(Job()).launch {
            val result = flow {
                emit("abc")
                emit("def")
                emit("ghi")
            }.join()
            log(result)
        }
    }

    private suspend fun Flow<String>.join(): String {
        val sb = StringBuilder()
        collect {
            sb.append(it).append(",")
        }
        return sb.toString()
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}