package ru.korobeynikov.p01rxjavabasicsobservableandobserver

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p01rxjavabasicsobservableandobserver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.fromArray("one", "two", "three").subscribeBy(
            onNext = { s -> log("onNext: $s") },
            onError = { e -> log("onError: $e") },
            onComplete = { log("onComplete") }
        )
    }

    private fun log(text: String) {
        Log.d("myLogs", text)
    }
}