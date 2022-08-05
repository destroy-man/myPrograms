package ru.korobeynikov.p1351loader

import android.content.AsyncTaskLoader
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeAsyncLoader(context: Context, args: Bundle?) : AsyncTaskLoader<String>(context) {

    private val LOG_TAG = "myLogs"
    private val PAUSE = 10

    companion object {
        const val ARGS_TIME_FORMAT = "time_format"
        const val TIME_FORMAT_SHORT = "h:mm:ss a"
        val TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss"
    }

    lateinit var format: String

    init {
        Log.d(LOG_TAG, "${hashCode()} create TimeAsyncLoader")
        if (args != null)
            format = args.getString(ARGS_TIME_FORMAT).toString()
        if (TextUtils.isEmpty(format))
            format = TIME_FORMAT_SHORT
    }

    override fun loadInBackground(): String? {
        Log.d(LOG_TAG, "${hashCode()} loadInBackground start")
        try {
            TimeUnit.SECONDS.sleep(PAUSE.toLong())
        } catch (e: InterruptedException) {
            return null
        }
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }
}