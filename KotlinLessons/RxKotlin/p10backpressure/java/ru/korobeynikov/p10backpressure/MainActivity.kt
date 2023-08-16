package ru.korobeynikov.p10backpressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.korobeynikov.p10backpressure.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.create { emitter ->
            var i = 1
            while (i <= 20) {
                if (emitter.isDisposed)
                    return@create
                emitter.onNext(i++)
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.computation()).doOnNext { int ->
            log("post $int")
        }.observeOn(Schedulers.io()).subscribeBy(
            onComplete = { log("onComplete") },
            onError = { e -> log("onError $e") },
            onNext = { int ->
                log("onNext $int")
                try {
                    TimeUnit.MILLISECONDS.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}