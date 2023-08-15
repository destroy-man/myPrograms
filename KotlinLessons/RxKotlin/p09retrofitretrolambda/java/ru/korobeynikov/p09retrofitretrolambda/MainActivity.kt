package ru.korobeynikov.p09retrofitretrolambda

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.korobeynikov.p09retrofitretrolambda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.just("1", "2", "3", "4", "5").map { s ->
            s.toInt()
        }.subscribeBy(
            onNext = { s -> log("onNext $s") },
            onError = { throwable -> log("onError $throwable") },
            onComplete = { log("onComplete") }
        )
    }

    private fun log(text: String) = Log.d("myLogs", text)
}