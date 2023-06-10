package ru.korobeynikov.p1351loader

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeAsyncLoader(context: Context, args: Bundle?) : AsyncTaskLoader<String>(context) {

    companion object {
        const val ARGS_TIME_FORMAT = "time_format"
        const val TIME_FORMAT_SHORT = "h:mm:ss a"
        const val TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss"
    }

    private val pause = 10
    private var format: String? = null

    init {
        Log.d(MainActivity.LOG_TAG, "${hashCode()} create TimeAsyncLoader")
        if (args != null)
            format = args.getString(ARGS_TIME_FORMAT)
        if (format?.isEmpty() == true)
            format = TIME_FORMAT_SHORT
    }

    override fun loadInBackground(): String? {
        Log.d(MainActivity.LOG_TAG, "${hashCode()} loadInBackground start")
        try {
            TimeUnit.SECONDS.sleep(pause.toLong())
        } catch (e: InterruptedException) {
            return null
        }
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }
}