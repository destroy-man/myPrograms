package ru.korobeynikov.p1351loader

import android.content.Context
import android.content.Loader
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeLoader(context: Context, args: Bundle?) : Loader<String>(context) {

    private val LOG_TAG = "myLogs"
    private val PAUSE = 10

    companion object {
        const val ARGS_TIME_FORMAT = "time_format"
        const val TIME_FORMAT_SHORT = "h:mm:ss a"
        const val TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss"
    }

    private var getTimeTask: GetTimeTask? = null
    private lateinit var format: String

    init {
        Log.d(LOG_TAG, "${hashCode()} create TimeLoader")
        if (args != null)
            format = args.getString(ARGS_TIME_FORMAT).toString()
        if (TextUtils.isEmpty(format))
            format = TIME_FORMAT_SHORT
    }

    override fun onStartLoading() {
        super.onStartLoading()
        Log.d(LOG_TAG, "${hashCode()} onStartLoading")
        if (takeContentChanged())
            forceLoad()
    }

    override fun onStopLoading() {
        super.onStopLoading()
        Log.d(LOG_TAG, "${hashCode()} onStopLoading")
    }

    override fun onForceLoad() {
        super.onForceLoad()
        Log.d(LOG_TAG, "${hashCode()} onForceLoad")
        if (getTimeTask != null)
            getTimeTask?.cancel(true)
        getTimeTask = GetTimeTask()
        getTimeTask?.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, format)
    }

    override fun onAbandon() {
        super.onAbandon()
        Log.d(LOG_TAG, "${hashCode()} onAbandon")
    }

    override fun onReset() {
        super.onReset()
        Log.d(LOG_TAG, "${hashCode()} onReset")
    }

    fun getResultFromTask(result: String?) {
        deliverResult(result)
    }

    inner class GetTimeTask : AsyncTask<String, Unit, String>() {

        override fun doInBackground(vararg params: String?): String? {
            Log.d(LOG_TAG, "${this@TimeLoader.hashCode()} doInBackground")
            try {
                TimeUnit.SECONDS.sleep(PAUSE.toLong())
            } catch (e: InterruptedException) {
                return null
            }
            val sdf = SimpleDateFormat(params[0], Locale.getDefault())
            return sdf.format(Date())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(LOG_TAG, "${this@TimeLoader.hashCode()} onPostExecute $result")
            getResultFromTask(result)
        }
    }
}