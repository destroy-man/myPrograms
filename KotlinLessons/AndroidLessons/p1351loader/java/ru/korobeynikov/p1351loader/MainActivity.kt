package ru.korobeynikov.p1351loader

import android.app.LoaderManager
import android.content.Loader
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {

    private val LOG_TAG = "myLogs"

    companion object {
        const val LOADER_TIME_ID = 1
        var lastCheckedId = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bndl = Bundle()
        bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat())
        loaderManager.initLoader(LOADER_TIME_ID, bndl, this)
        lastCheckedId = rgTimeFormat.checkedRadioButtonId
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String>? {
        var loader: Loader<String>? = null
        if (id == LOADER_TIME_ID) {
            loader = TimeAsyncLoader(this, args)
            Log.d(LOG_TAG, "onCreateLoader: ${loader.hashCode()}")
        }
        return loader
    }

    override fun onLoadFinished(loader: Loader<String>?, result: String?) {
        Log.d(LOG_TAG, "onLoadFinished for loader ${loader.hashCode()}, result = $result")
        tvTime.text = result
    }

    override fun onLoaderReset(loader: Loader<String>?) {
        Log.d(LOG_TAG, "onLoaderReset for loader ${loader.hashCode()}")
    }

    fun getTimeClick(v: View) {
        val loader: Loader<String>
        val id = rgTimeFormat.checkedRadioButtonId
        if (id == lastCheckedId)
            loader = loaderManager.getLoader(LOADER_TIME_ID)
        else {
            val bndl = Bundle()
            bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat())
            loader = loaderManager.restartLoader(LOADER_TIME_ID, bndl, this)
            lastCheckedId = id
        }
        loader.forceLoad()
    }

    private fun getTimeFormat(): String {
        var result = TimeLoader.TIME_FORMAT_SHORT
        when (rgTimeFormat.checkedRadioButtonId) {
            R.id.rdShort -> result = TimeLoader.TIME_FORMAT_SHORT
            R.id.rdLong -> result = TimeLoader.TIME_FORMAT_LONG
        }
        return result
    }

    fun observerClick(v: View) {
        Log.d(LOG_TAG, "observerClick")
        val loader = loaderManager.getLoader<String>(LOADER_TIME_ID)
        val observer = loader.ForceLoadContentObserver()
        v.postDelayed({
            observer.dispatchChange(false)
        }, 5000)
    }
}