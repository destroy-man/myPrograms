package ru.korobeynikov.p1351loader

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.loader.content.Loader
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class TimeLoader(context: Context, args: Bundle?) : Loader<String>(context) {

    companion object {
        const val ARGS_TIME_FORMAT = "time_format"
        const val TIME_FORMAT_SHORT = "h:mm:ss a"
        const val TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss"
    }

    private val pause = 10
    private var executorService: ExecutorService? = null
    private var format: String? = null

    init {
        Log.d(MainActivity.LOG_TAG, "${hashCode()} create TimeLoader")
        if (args != null)
            format = args.getString(ARGS_TIME_FORMAT)
        if (format?.isEmpty() == true)
            format = TIME_FORMAT_SHORT
    }

    override fun onStartLoading() {
        super.onStartLoading()
        Log.d(MainActivity.LOG_TAG, "${hashCode()} onStartLoading")
        if (takeContentChanged())
            forceLoad()
    }

    override fun onStopLoading() {
        super.onStopLoading()
        Log.d(MainActivity.LOG_TAG, "${hashCode()} onStopLoading")
    }

    override fun forceLoad() {
        super.forceLoad()
        Log.d(MainActivity.LOG_TAG, "${hashCode()} onForceLoad")
        executorService?.shutdown()
        executorService = Executors.newSingleThreadExecutor()
        executorService?.execute {
            Log.d(MainActivity.LOG_TAG, "${hashCode()} execute")
            try {
                TimeUnit.SECONDS.sleep(pause.toLong())
            } catch (e: InterruptedException) {
                return@execute
            }
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val result = sdf.format(Date())
            Log.d(MainActivity.LOG_TAG, "${hashCode()} post execute $result")
            getResultFromTask(result)
        }
    }

    override fun onAbandon() {
        super.onAbandon()
        Log.d(MainActivity.LOG_TAG, "${hashCode()} onAbandon")
    }

    override fun onReset() {
        super.onReset()
        Log.d(MainActivity.LOG_TAG, "${hashCode()} onReset")
    }

    private fun getResultFromTask(result: String) = deliverResult(result)
}