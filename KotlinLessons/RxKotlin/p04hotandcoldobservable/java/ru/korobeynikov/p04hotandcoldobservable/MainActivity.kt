package ru.korobeynikov.p04hotandcoldobservable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import ru.korobeynikov.p04hotandcoldobservable.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        var needClose = true
        val observer1 = object : Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                if (needClose) {
                    Thread {
                        TimeUnit.MILLISECONDS.sleep(4000)
                        log("observer1 unsubscribe")
                        d.dispose()
                    }.start()
                    needClose = false
                }
            }

            override fun onComplete() {
                log("observer1 onComplete")
            }

            override fun onError(e: Throwable) {}

            override fun onNext(aLong: Long) {
                log("observer1 onNext value = $aLong")
            }
        }
        val observer2 = object : Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                Thread {
                    TimeUnit.MILLISECONDS.sleep(2500)
                    log("observer2 unsubscribe")
                    d.dispose()
                }.start()
            }

            override fun onComplete() {
                log("observer2 onComplete")
            }

            override fun onError(e: Throwable) {}

            override fun onNext(aLong: Long) {
                log("observer2 onNext value = $aLong")
            }
        }
        val observable = Observable.interval(1, TimeUnit.SECONDS).take(10).cache()
        Thread {
            TimeUnit.MILLISECONDS.sleep(1500)
            log("observer1 subscribe")
            observable.subscribe(observer1)
        }.start()
        Thread {
            TimeUnit.MILLISECONDS.sleep(4000)
            log("observer2 subscribe")
            observable.subscribe(observer2)
        }.start()
        Thread {
            TimeUnit.MILLISECONDS.sleep(7500)
            log("observer1 subscribe")
            observable.subscribe(observer1)
        }.start()
    }

    fun log(text: String) = Log.d("myLogs", text)
}