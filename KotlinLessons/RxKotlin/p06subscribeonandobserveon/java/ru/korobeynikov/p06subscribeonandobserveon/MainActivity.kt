package ru.korobeynikov.p06subscribeonandobserveon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.korobeynikov.p06subscribeonandobserveon.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.create { emitter ->
            log("subscribe")
            for (i in 0 until 3) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                emitter.onNext(i)
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).map(func)
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onComplete = { log("observer onComplete") },
                onNext = { value -> log("observer onNext value = $value") }
        )
        log("done")
    }

    private fun log(text: String) = Log.d("myLogs", text)

    private val func = Function<Int, Int> { int ->
        log("func $int")
        int * 10
    }
}