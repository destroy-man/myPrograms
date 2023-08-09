package ru.korobeynikov.p03subscriptioncreateownobservable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.korobeynikov.p03subscriptioncreateownobservable.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Observable.create { emitter ->
            for (i in 0 until 10) {
                try {
                    TimeUnit.SECONDS.sleep(1)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                emitter.onNext(i)
            }
            if (emitter.isDisposed)
                return@create
            emitter.onComplete()
        }.subscribeOn(Schedulers.io()).subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                window.decorView.postDelayed({
                    log("unsubscribe")
                    d.dispose()
                }, 4500)
            }

            override fun onComplete() {
                log("onComplete")
            }

            override fun onError(e: Throwable) {
                log("onError: $e")
            }

            override fun onNext(i: Int) {
                log("onNext: $i")
            }
        })
    }

    fun log(text: String) = Log.d("myLogs", text)
}