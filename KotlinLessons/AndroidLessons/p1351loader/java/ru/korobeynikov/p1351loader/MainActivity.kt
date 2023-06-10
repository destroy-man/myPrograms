package ru.korobeynikov.p1351loader

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import ru.korobeynikov.p1351loader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {

    companion object {
        const val LOG_TAG = "myLogs"
        const val LOADER_TIME_ID = 1
        var lastCheckedId = 0
    }

    private lateinit var tvTime: TextView
    private lateinit var rgTimeFormat: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvTime = binding.tvTime
        rgTimeFormat = binding.rgTimeFormat
        val bndl = Bundle()
        bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat())
        LoaderManager.getInstance(this).initLoader(LOADER_TIME_ID, bndl, this)
        lastCheckedId = rgTimeFormat.checkedRadioButtonId
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        val loader = TimeAsyncLoader(this, args)
        Log.d(LOG_TAG, "onCreateLoader: ${loader.hashCode()}")
        return loader
    }

    override fun onLoadFinished(loader: Loader<String>, result: String?) {
        Log.d(LOG_TAG, "onLoadFinished for loader ${loader.hashCode()}, result = $result")
        tvTime.text = result
    }

    override fun onLoaderReset(loader: Loader<String>) {
        Log.d(LOG_TAG, "onLoaderReset for loader ${loader.hashCode()}")
    }

    fun getTimeClick() {
        val loader: Loader<String>?
        val id = rgTimeFormat.checkedRadioButtonId
        if (id == lastCheckedId)
            loader = LoaderManager.getInstance(this).getLoader(LOADER_TIME_ID)
        else {
            val bndl = Bundle()
            bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat())
            loader = LoaderManager.getInstance(this).restartLoader(LOADER_TIME_ID, bndl, this)
            lastCheckedId = id
        }
        loader?.forceLoad()
    }

    private fun getTimeFormat() = when (rgTimeFormat.checkedRadioButtonId) {
        R.id.rdLong -> TimeLoader.TIME_FORMAT_LONG
        else -> TimeLoader.TIME_FORMAT_SHORT
    }

    fun observerClick(v: View) {
        Log.d(LOG_TAG, "observerClick")
        val loader = LoaderManager.getInstance(this).getLoader<String>(LOADER_TIME_ID)
        val observer = loader?.ForceLoadContentObserver() as ContentObserver
        v.postDelayed({
            observer.dispatchChange(false, null, 0)
        }, 5000)
    }
}