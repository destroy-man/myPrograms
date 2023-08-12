package ru.korobeynikov.p07errorprocessing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p07errorprocessing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.just("1", "2", "a", "4", "5").map { s ->
            s.toLong()
        }.retryWhen { observableErrors ->
            log("retryWhen")
            observableErrors.take(3)
        }.subscribeBy(
            onComplete = { log("onComplete") },
            onError = { e -> log("onError $e") },
            onNext = { aLong -> log("onNext $aLong") }
        )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}