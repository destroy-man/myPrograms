package ru.korobeynikov.p03livedataadditionalcapabilities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.toPublisher
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p03livedataadditionalcapabilities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val liveData: LiveData<String> = MutableLiveData("a")
        Flowable.fromPublisher(liveData.toPublisher(this)).subscribeBy { log(it) }
    }

    private fun log(text: String) = Log.d("myLogs", text)
}