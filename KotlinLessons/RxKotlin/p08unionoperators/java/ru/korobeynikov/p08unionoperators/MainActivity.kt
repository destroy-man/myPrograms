package ru.korobeynikov.p08unionoperators

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p08unionoperators.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.just("A", "B", "C", "D", "E").concatMap { s ->
            Observable.just(s).delay(100, TimeUnit.MILLISECONDS)
        }.subscribeBy(
            onComplete = { log("onComplete") },
            onNext = { next -> log("onNext $next") }
        )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}