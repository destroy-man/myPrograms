package ru.korobeynikov.p03livedataextrapossibilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val liveData = MutableLiveData<String>()
        liveData.value = "new value"
        val flowable = Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(this, liveData))
        flowable.observeOn(Schedulers.io()).subscribeBy { value ->
            Log.d(logTag, value)
        }
    }
}