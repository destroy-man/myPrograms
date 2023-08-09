package ru.korobeynikov.p02operatorsaction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p02operatorsaction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.fromArray("one", "two", "three").subscribeBy(
            onNext = { s -> log("onNext: $s") }
        )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}